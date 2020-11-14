package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

public class RequestMessageCacheController implements Runnable {
    private volatile boolean stopped = false;

    public void stop() {
        stopped = true;
    }

    public boolean isStoped() {
        return stopped;
    }


    @Override
    public void run() {
        while (!stopped){
            RequestMessage requestMessage = LevelTwoMessageCache.getRequestMessage();
            if(requestMessage!=null){
                System.out.println("level2   "+requestMessage.toString());
                String topic = requestMessage.getRequestHeader().getTopic();
                if(ClientCluster.getChannelsByTopic(topic).size()<=1){
                    LevelTwoMessageCache.pushRequestMessage(requestMessage);
                }else{
                    ClientCluster.getChannelsByTopic(topic).forEach((key,value)->{
                        if(!key.equals(requestMessage.getRequestHeader().getMsgId()) && value.isConnected())
                            value.getChannel().writeAndFlush(requestMessage);
                    });
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
