package com.servicematrix.matrixmq.broker.protocol;

import com.servicematrix.matrixmq.broker.AckConnectQueue;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.broker.AckConnectMessage;
import com.servicematrix.matrixmq.msg.client.ConnectMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import static com.servicematrix.matrixmq.broker.MQBroker.BROKERID;

public class Connect {

    private static final Logger logger = Logger.getLogger(Connect.class);

    public void processConnect(Channel channel, ConnectMessage connectMessage){
        if(!RemoteClientCluster.containsClient(channel)){
            RemoteClientCluster.addClient(connectMessage,channel);
        }
        else{
            logger.error("Client "+channel.remoteAddress()+" has connected");
            channel.close();
            return;
        }
        connectMessage.setChannelId(channel.id());
        AckConnectQueue.pushConnectMessage(connectMessage);
    }


}
