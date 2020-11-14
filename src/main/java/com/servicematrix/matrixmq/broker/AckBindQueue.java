package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.BindMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AckBindQueue {
    private static ConcurrentLinkedQueue<BindMessage> ackBindQueue = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<BindMessage> getAckBindQueue() {
        return ackBindQueue;
    }

    public static void setAckBindQueue(ConcurrentLinkedQueue<BindMessage> ackBindQueue) {
        AckBindQueue.ackBindQueue = ackBindQueue;
    }

    public static boolean pushBindMessage(BindMessage bindMessage){
        return ackBindQueue.offer(bindMessage);
    }

    public static BindMessage getBindMessage(){
        return ackBindQueue.poll();
    }
}
