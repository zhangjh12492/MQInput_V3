package com.wfj.mq.service.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.ItgMsgHeaderDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.entity.InboundConf;
import com.wfj.mq.entity.InboundQueueRef;
import com.wfj.mq.service.MqConfService;
import com.wfj.mq.service.MsgHelperService;
import com.wfj.mq.utils.ItgConst;
import com.wfj.mq.utils.ItgConstent;
import com.wfj.persist.SimpleGenericDAO;
import com.wfj.utils.FieldsAnnoatation.InterfaceBeanUtil;
import com.wfj.utils.JsonUtils;
import com.wfj.utils.cache.Cache;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by MaYong on 2014/12/18.
 */

@Service("msgHelperService")
public class MsgHelperServiceImpl implements MsgHelperService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "mqConfService")
    private MqConfService mqConfService;
    private SimpleGenericDAO<InboundConf, Integer> inboundConfDao;
    private SimpleGenericDAO<InboundQueueRef, Integer> inboundQueueRefDao;
    @Resource(name = "redisCache")
    private Cache cache;


    @Override
    public ItgCallbackDto parserCallBackMessage(String msg) {
        ItgCallbackDto callbackDto = JsonUtils.jsonToBean(msg, ItgCallbackDto.class);
        return callbackDto;
    }

    /*Linda 2014-2-5 Inbound报文转对象时需要验证，但OutBound报文解析成时不需要做检查
     * 所有从parserMessage方法中去除报文有效性检查方法：validatorMsg(MessageDto messageDto)
     * 
     * */
    @Override
    public MessageDto parserMessage(String msg) throws JSONException {
        MessageDto messageDto = new MessageDto();
        HashMap<String, Class> map = new HashMap<String, Class>();
        JSONObject jsonObject = JSONObject.fromObject(msg);//Linda--需要Morpher
        ItgMsgHeaderDto itgMsgHeaderDto = JsonUtils.jsonToBean(jsonObject.getString("header"), ItgMsgHeaderDto.class);
/*
        if (!jsonObject.has("destCallType")) {
            itgMsgHeaderDto.setField2("destCallType=false");
        }
*/
        messageDto.setHeader(itgMsgHeaderDto);
        messageDto.setData(jsonObject.get("data"));

        // validatorMsg(messageDto); //Linda：不需要，屏蔽掉
        return messageDto;
    }

    /*Update by Linda 2015-2-2 需要完善如下内容
     * 1.按照报文大小进行检查，不超过配置最大值
     * 2.按照数据字典内容对报文填写内容进行有效性检查
     * */
    @Override
    public String validatorMsg(MessageDto messageDto, String message) throws SQLException, Exception {
        InboundConf qryConf = mqConfService.getInboundConf(messageDto);
        ItgMsgHeaderDto headerDto = messageDto.getHeader();
        if (qryConf == null) {
            return "未配置接入";
        }
        if (message.length() > qryConf.getMaxLength() * 1024) {
            //报文超长
            return ItgConst.ITG_MESSAGE_ELEMENT_NAME_LENGTH;
        }
        String msg = InterfaceBeanUtil.val(headerDto);
        if (msg != null) {
            return msg;
        }
        if (qryConf.getExchangeType() == ItgConstent.ExchangeType.direct.getCode()) {
            //属于DIRECT类型，则传入的routeKey不能为空
            if (headerDto.getRouteKey() == null || headerDto.getRouteKey().trim().length() == 0) {
                return "DIRECT类型传入的routeKey不能为空";
            }
            if (headerDto.getDestUrl() == null || headerDto.getDestUrl().trim().length() == 0) {
                return "DIRECT类型传入的destUrl不能为空";
            }
            if (headerDto.getDestCallType() == null) {
                logger.error("DIRECT类型传入的destCallType不能为空");
                return "DIRECT类型传入的destCallType不能为空";
            }
        } else if (qryConf.getExchangeType() == ItgConstent.ExchangeType.topic.getCode()) {
            //属于TOPIC类型，则传入的routeKey不能为空
            if (headerDto.getRouteKey() == null || headerDto.getRouteKey().trim().length() == 0) {
                return "TOPIC类型传入的routeKey不能为空";
            }
        }
        return null;
    }


    public Integer authMsg(MessageDto messageDto) throws SQLException {
        ItgMsgHeaderDto headerDto = messageDto.getHeader();
        String key = "authMsg:" + headerDto.getSourceSysID() + "===" + headerDto.getServiceID();
        Integer count = null;
        try {
            count = (Integer) cache.getObject(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (count == null) {
            InboundConf conf = new InboundConf();
            conf.setSystemNo(headerDto.getSourceSysID());
            conf.setServiceNo(headerDto.getServiceID());
            count = inboundQueueRefDao.findListCountByStatementCond("selectBindCount", conf);
            try {
                cache.setObject(key, count, 3000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return count;
    }


    @Override
    public MessageDto createMsgUUID(MessageDto messageDto) {
        UUID uuid = UUID.randomUUID();
        messageDto.getHeader().setMessageID(uuid.toString());
        return messageDto;
    }

    @Autowired
    public void setDpClient(SqlMapClient dpClient) {
        this.inboundConfDao = new SimpleGenericDAO<InboundConf, Integer>(dpClient, InboundConf.class);
        this.inboundQueueRefDao = new SimpleGenericDAO<InboundQueueRef, Integer>(dpClient, InboundQueueRef.class);
    }


}
