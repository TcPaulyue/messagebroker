package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LevelTwoMessageCache {
    private static ConcurrentLinkedQueue<RequestMessage> requestMessageQueue_2 = new ConcurrentLinkedQueue<>();

    public static boolean pushRequestMessage(RequestMessage RequestMessage){
        return requestMessageQueue_2.offer(RequestMessage);
    }

    public static RequestMessage getRequestMessage(){
        return requestMessageQueue_2.poll();
    }

}
