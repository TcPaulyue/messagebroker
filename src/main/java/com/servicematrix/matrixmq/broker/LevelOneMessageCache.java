package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LevelOneMessageCache {
    private static ConcurrentLinkedQueue<RequestMessage> requestMessageQueue_1 = new ConcurrentLinkedQueue<>();

    public static boolean pushRequestMessage(RequestMessage RequestMessage){
        return requestMessageQueue_1.offer(RequestMessage);
    }

    public static RequestMessage getRequestMessage(){
        return requestMessageQueue_1.poll();
    }

    public static ConcurrentLinkedQueue<RequestMessage> getRequestMessageQueue_1() {
        return requestMessageQueue_1;
    }

    public static void setRequestMessageQueue_1(ConcurrentLinkedQueue<RequestMessage> requestMessageQueue_1) {
        LevelOneMessageCache.requestMessageQueue_1 = requestMessageQueue_1;
    }
}
