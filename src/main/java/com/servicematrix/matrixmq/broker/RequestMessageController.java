package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

public class RequestMessageController implements Runnable {
    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;
    }

    public boolean isStoped() {
        return stopped;
    }

    @Override
    public void run() {
        while(!stopped){
            RequestMessage requestMessage = LevelOneMessageCache.getRequestMessage();
            if(requestMessage!=null){
                System.out.println(requestMessage.toString());
                String topic = requestMessage.getRequestHeader().getTopic();
                if(ClientCluster.getChannelsByTopic(topic).size()<=1){
                    LevelTwoMessageCache.pushRequestMessage(requestMessage);
                    continue;
                }
                ClientCluster.getChannelsByTopic(topic).forEach((key,value)->{
                    if(!key.equals(requestMessage.getRequestHeader().getMsgId()) && value.isConnected())
                        value.getChannel().writeAndFlush(requestMessage);
                });
            }
        }
    }
}
