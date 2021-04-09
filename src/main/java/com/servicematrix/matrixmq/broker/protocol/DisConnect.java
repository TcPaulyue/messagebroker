package com.servicematrix.matrixmq.broker.protocol;

import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.broker.AckDisConnectMessage;
import com.servicematrix.matrixmq.msg.client.DisConnectMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import static com.servicematrix.matrixmq.broker.MQBroker.BROKERID;

public class DisConnect {
    private static final Logger logger = Logger.getLogger(Connect.class);

    public void processDisConnect(Channel channel, DisConnectMessage disConnectMessage){
        if(RemoteClientCluster.containsClient(channel)){
            RemoteClientCluster.removeClient(disConnectMessage,channel);
        }
        else{
            logger.error("Thread-DisConnect: "+"Client "+channel.remoteAddress()+" has disConnected");
            return;
        }
        AckDisConnectMessage ackDisConnectMessage = new AckDisConnectMessage();
        ackDisConnectMessage.setBrokerId(BROKERID);
        ackDisConnectMessage.setTime(System.currentTimeMillis());
        channel.writeAndFlush(ackDisConnectMessage).addListener(future -> {
            if(future.isSuccess()){
                logger.info("Thread-DisConnect: "+channel.remoteAddress()+" disconnect...");
            }
        });
        channel.close();

    }
}
