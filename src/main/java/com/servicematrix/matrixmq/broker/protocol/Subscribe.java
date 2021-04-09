package com.servicematrix.matrixmq.broker.protocol;

import com.servicematrix.matrixmq.broker.RequestMessageController;
import com.servicematrix.matrixmq.broker.applicationContext.ApplicationContextCluster;
import com.servicematrix.matrixmq.broker.clientCluster.RemoteClientCluster;
import com.servicematrix.matrixmq.msg.client.AppContextMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Subscribe {
    private static final Logger logger = Logger.getLogger(Subscribe.class);
    public static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);


    public void processSubscribe(Channel channel,AppContextMessage appContextMessage){
        appContextMessage.setChannelId(channel.id());
        if(!RemoteClientCluster.getClientState(channel)){
            logger.error("client disconnect to broker...");
            return;
        }
        if(ApplicationContextCluster.addApplicationContext(appContextMessage,channel)){
            executorService.execute(new RequestMessageController(appContextMessage.getAppContextId()));
        }
    }
}
