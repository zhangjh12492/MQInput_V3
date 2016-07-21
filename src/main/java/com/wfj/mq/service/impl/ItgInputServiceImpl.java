package com.wfj.mq.service.impl;

import com.rabbitmq.client.Channel;
import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.dto.MessageDtoByDestCallType;
import com.wfj.mq.service.ItgInputService;
import com.wfj.mq.service.MessageLogService;
import com.wfj.mq.service.MqService;
import com.wfj.mq.service.MsgHelperService;
import com.wfj.mq.utils.ItgConst;
import com.wfj.mq.utils.ItgConstent;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * Created by MaYong on 2014/12/4.
 */
@Service("itgInputService")
public class ItgInputServiceImpl implements ItgInputService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "mqService")
    private MqService mqService;
    private Hashtable<String, Channel> channelHashtable = new Hashtable<String, Channel>();
    @Resource(name = "msgHelperService")
    private MsgHelperService msgHelperService;
    @Resource(name = "messageLogService")
    private MessageLogService messageLogService;

    @Override
    public ItgCallbackDto input(String message) throws Exception {

        ItgCallbackDto callbackDto = new ItgCallbackDto();
        Long messageLogId = null;
        //1.记录访问日志
        //itgTxTraceService.logTrace(null);
        //生成报文对象
        logger.debug("== 解析报文 ==" + message);
        MessageDto messageDto = null;
        try {
            messageDto = msgHelperService.parserMessage(message);
            //如果为空，设置为默认rest方式post调用
            if (messageDto.getHeader().getDestCallType() == null) {
                messageDto.getHeader().setDestCallType(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callbackDto.setRespStatus(ItgConstent.ResultType.失败.getCode());
            callbackDto.setBizDesc(ItgConst.ITG_MESSAGE_LEGEL_ERR_DESC);
            callbackDto.setBizCode(ItgConst.ITG_MESSAGE_LEGEL_ERR_CODE);
            return callbackDto;
        }
        //2.解析报文头，进行报文有效性检查（检查失败，抛出异常，返回失败响应报文）
        //i.报文不符合规范(包括非JSON格式及其他不符合王府井报文规范header，data)
        logger.debug("== Inbound报文需要进行报文合法性检查 ==");
        //Add by Linda 20150203
        String msg = msgHelperService.validatorMsg(messageDto, message);
        if (msg != null) {
            callbackDto.setRespStatus(ItgConstent.ResultType.失败.getCode());
            callbackDto.setBizDesc(msg);
            callbackDto.setBizCode(ItgConst.ITG_MESSAGE_LEGEL_ERR_CODE);
            return callbackDto;

        }
        //ii.无授权（调用配置信息，1、检查登录用户名与口令，如果口令加密，需要调用解密方法，是否合法用户2、检查授权情况，能不能发出某个业务对象）
        //通过应用表定义权限关系，暂时不使用rabbitmq的user 权限定义
        logger.debug("== 报文授权检查 ==");
        Integer count = msgHelperService.authMsg(messageDto);
        if (count == null || count == 0) {
            //检查未通过
            callbackDto.setRespStatus(ItgConstent.ResultType.失败.getCode());
            callbackDto.setBizDesc("未授权");
            callbackDto.setBizCode(ItgConst.ITG_MESSAGE_AUTHORIZATION_ERR_DESC);
            return callbackDto;
        }
        //iii.报文体超长,放到i中
        //===============自此上，返回错误信息给外部系统
        MessageDto messageUUIDDto = null;
        String messageId = null;
        String messageType = "1";//新报文
        logger.debug("== 生成报文UUID ==");
        messageUUIDDto = msgHelperService.createMsgUUID(messageDto);
        messageId = messageUUIDDto.getHeader().getMessageID();
        //插入messageID,但不要影响原报文
        //add by mayong
/*
        JSONObject jsonObject = JSONObject.fromObject(message);
        jsonObject.getJSONObject("header").put("messageID", messageId);
        String uuidMsgString = jsonObject.toString();
        System.out.println("==========" + uuidMsgString);
*/
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(message);
        String uuidMsgString = null;
        if (!jsonNode.has("destCallType")) {
            //不存在destCallType,就是为了兼容以前的旧版本
            MessageDtoByDestCallType callTypeDto = mapper.readValue(message, MessageDtoByDestCallType.class);
            callTypeDto.getHeader().setMessageID(messageId);
            uuidMsgString =  mapper.writeValueAsString(callTypeDto);
        } else {
            MessageDto srcDto = mapper.readValue(message, MessageDto.class);
            srcDto.getHeader().setMessageID(messageId);
            uuidMsgString = mapper.writeValueAsString(srcDto);
        }

        //报文创建成功后，即可存储
        try {
            //存储报文
            messageLogService.saveMessageAndTxinfo(messageDto, uuidMsgString);
            logger.debug(messageId + "== 存储报文完成 ==");
        } catch (SQLException e) {
            //如果存储不成功，说明数据库存在问题，为致命错误，系统异常返回
            logger.error(messageId + "== 系统异常，错误返回:" + e.getMessage());
            callbackDto.setRespStatus(ItgConstent.ResultType.失败.getCode());
            callbackDto.setBizDesc("系统异常，错误返回:" + e.getMessage());
            return callbackDto;
        }
        try {
            mqService.sendMessage(messageDto, uuidMsgString);
            logger.debug("== 存储发送至队列 ==");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //log要把messageId打出来
        callbackDto.setServiceID(messageUUIDDto.getHeader().getServiceID());
        callbackDto.setMessageID(messageId);
        callbackDto.setRespStatus("1");
        callbackDto.setRespStatus(ItgConstent.ResultType.成功.getCode());
        callbackDto.setBizDesc(ItgConstent.ResultType.成功.getValue());
        return callbackDto;
    }


    @Override
    public void sendMsgToMq(MessageDto msgInfo) throws Exception {

    }
}
