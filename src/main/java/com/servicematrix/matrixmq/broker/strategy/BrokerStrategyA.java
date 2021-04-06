package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientInfo;
import com.servicematrix.matrixmq.msg.client.Location;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public class BrokerStrategyA extends BrokerStrategy<List<RemoteClientInfo>,Channel> {

    public BrokerStrategyA(List<RemoteClientInfo> remoteClientInfos) {
        super(remoteClientInfos);
    }

    @Override
    public List<Channel> routing(RequestMessage requestMessage) {
        //return this.getT();
        List<Channel> channels = new ArrayList<>();
        Location location = requestMessage.getRequestHeader().getLocation();
        this.getT().forEach(remoteClientInfo -> {
            if(remoteClientInfo.getLocation().calcDistance(location)<10.0)
                channels.add(remoteClientInfo.getChannel());
        });
        return channels;
    }
}
