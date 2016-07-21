package com.wfj.mq.service.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.wfj.mq.entity.GroupConf;
import com.wfj.mq.entity.ServerConf;
import com.wfj.mq.service.ChannelService;
import com.wfj.persist.SimpleGenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by MaYong on 2015/4/18.
 */
@Service("channelService")
public class ChannelServiceImpl implements ChannelService {

    private SimpleGenericDAO<ServerConf, Integer> serverConfDao;
    private SimpleGenericDAO<GroupConf, Integer> groupConfDao;

    private Hashtable<Integer, ConnectionFactory> factorys = new Hashtable<Integer, ConnectionFactory>();
    private Hashtable<Integer, Connection> connections = new Hashtable<Integer, Connection>();
    private Hashtable<String, Channel> channels = new Hashtable<String, Channel>();

    @Override
    public Channel getChannel(Integer groupSid, Integer inboundSid) throws Exception {
        String key = groupSid + "==" + inboundSid;
        Channel channel = channels.get(key);
        if (channel == null || !channel.isOpen()) {
            channel = getConn(groupSid).createChannel();
            channels.put(key, channel);
        }
        return channel;
    }

    private Connection getConn(Integer groupSid) throws Exception {
        Connection connection = connections.get(groupSid);
        if (connection == null || !connection.isOpen()) {
            ServerConf qryServer = new ServerConf();
            qryServer.setGroupSid(groupSid);
            List<ServerConf> serverList = serverConfDao.findListByCond(qryServer);
            Address[] addrArr = new Address[serverList.size()];
            for (int i = 0; i < serverList.size(); i++) {
                String ip = serverList.get(i).getServerIp();
                Integer port = serverList.get(i).getServerPort();
                addrArr[i] = new Address(ip, port);
            }
            connection = getFactory(groupSid).newConnection(addrArr);
            connections.put(groupSid, connection);
        }
        return connection;
    }

    private ConnectionFactory getFactory(Integer groupSid) throws SQLException {
        ConnectionFactory factory = factorys.get(groupSid);
        if (factory == null) {
            //1、取Group
            factory = new ConnectionFactory();
            GroupConf group = groupConfDao.findById(groupSid);
            factory.setUsername(group.getUserName());
            factory.setPassword(group.getPassword());
            factory.setVirtualHost("/");
            factorys.put(groupSid, factory);
        }
        return factory;
    }

    @Autowired
    public void setDpClient(SqlMapClient dpClient) {
        this.serverConfDao = new SimpleGenericDAO<ServerConf, Integer>(dpClient, ServerConf.class);
        this.groupConfDao = new SimpleGenericDAO<GroupConf, Integer>(dpClient, GroupConf.class);
    }

}
