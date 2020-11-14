package com.servicematrix.matrixmq.broker.strategy;


import com.servicematrix.matrixmq.broker.RemoteClientInfo;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerStrategyA implements BrokerStrategy {
    @Override
    public List<Channel> routing(RequestMessage requestMessage, ConcurrentHashMap<String, RemoteClientInfo> hashMap) {
        List<Channel> channels = new ArrayList<>();
        hashMap.forEach((key,value)->{
            if(value.isConnected())
                channels.add(value.getChannel());
        });
        return channels;
    }
}
