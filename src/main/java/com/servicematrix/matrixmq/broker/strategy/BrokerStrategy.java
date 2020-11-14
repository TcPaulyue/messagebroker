package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.broker.RemoteClientInfo;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface BrokerStrategy {
    List<Channel> routing(RequestMessage requestMessage, ConcurrentHashMap<String, RemoteClientInfo> hashMap);
}
