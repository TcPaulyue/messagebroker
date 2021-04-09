package com.servicematrix.matrixmq.broker;

import com.servicematrix.matrixmq.msg.client.ConnectMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AckConnectQueue {
    private static ConcurrentLinkedQueue<ConnectMessage> ackConnectQueue = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<ConnectMessage> getAckConnectQueue() {
        return ackConnectQueue;
    }

    public static void setAckConnectQueue(ConcurrentLinkedQueue<ConnectMessage> ackConnectQueue) {
        AckConnectQueue.ackConnectQueue = ackConnectQueue;
    }

    public static boolean pushConnectMessage(ConnectMessage ConnectMessage){
        return ackConnectQueue.offer(ConnectMessage);
    }

    public static ConnectMessage getConnectMessage(){
        return ackConnectQueue.poll();
    }
}
