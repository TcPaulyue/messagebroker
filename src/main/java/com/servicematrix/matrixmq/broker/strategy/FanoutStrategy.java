package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.broker.clientCluster.RemoteClient;
import com.servicematrix.matrixmq.msg.client.RequestMessage;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public class FanoutStrategy extends BrokerStrategy<List<RemoteClient>,Channel> {

    public FanoutStrategy(List<RemoteClient> remoteClients) {
        super(remoteClients);
    }

    @Override
    public List<Channel> routing(RequestMessage requestMessage) {
        //return this.getT();
        List<Channel> channels = new ArrayList<>();
        //Location location = requestMessage.getRequestHeader().getLocation();
        this.getT().forEach(remoteClient -> {
            if(remoteClient.isConnected()&&!requestMessage.getChannelId().equals(remoteClient.getChannel().id()))
                channels.add(remoteClient.getChannel());
        });
        return channels;
    }
}
