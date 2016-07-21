package com.wfj.mq.service;

import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.MessageDto;

/**
 * Created by MaYong on 2014/12/4.
 *
 * @version 1.0
 * @Title 集成框架接入服入口
 * 业务异常与技术异常分开
 * 一般业务异常不需要回滚
 */
public interface ItgInputService {
    /**
     * @param message 符合标准格式的消息
     * @return
     * @Author MaYong
     * @date 2014-12-04 14:57
     * @version 1.0
     * @see 集成框架消息入口
     * 1、子方法串行开发
     * 1）调用接口时，要记录访问日志
     * 2）其他环节
     */
    public ItgCallbackDto input(String message)throws Exception;





    /**
     * @param msgInfo
     * @return
     * @Author MaYong
     * @date 2014-12-04 15:12
     * @version 1.0
     * @Description: TODO (调用配置信息，并发送报文)
     * @see 主要处理逻辑，主入口，上面作废
     * 1、记录访问日志
     * 2、解析报文头，进行报文有效性检查（检查失败，抛出异常，返回失败响应报文）
     * i.报文不符合规范(包括非JSON格式及其他不符合王府井报文规范header，data)
     * ii.无授权（调用配置信息，1、检查登录用户名与口令，如果口令加密，需要调用解密方法，是否合法用户2、检查授权情况，能不能发出某个业务对象）
     * iii.报文体超长
     * 3、生成报文UUID（自此需要内部处理，需要重发到MQ，需要再识别出服务，支持报文重发，暂定为quartz）
     * 4、调用配置信息（MQ配置等），准备发送
     * 5、发送报文到MQ,记录日志
     * 6、根据MQ响应记录日志
     * 6.1成功
     * 6.2失败（哪些需要集成框架模暂存，哪些不需要，配置信息造成，通信错误RabbitMQ造成的，应该重发，到这里应该是都需要，因为已经过了报文头检查）
     * i.交换机异常，什么异常信息
     * ii。通信异常，rabbitmq不存在,什么异常信息
     * iii.配置信息不存在，什么异常信息
     * iV.队列深度，超长，什么异常信息
     * 7、根据发送结果判断生成哪种响应报文createCallbackMsg(MessageDto msgInfo)
     * 8、记录应用响应报文日志
     */
    public void sendMsgToMq(MessageDto msgInfo) throws Exception;

//    public BackInfoDto createCallbackMsg(MessageDto msgInfo);

    //一、报文重发，另写接口处理
    //

}
