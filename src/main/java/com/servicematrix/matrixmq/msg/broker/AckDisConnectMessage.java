package com.servicematrix.matrixmq.msg.broker;

import com.servicematrix.matrixmq.msg.MessageType;

public class AckDisConnectMessage extends BrokerMessage{
    public AckDisConnectMessage() {
        this.setMessageType(MessageType.BROKER_ACK_DISCONNECT);
    }

    public AckDisConnectMessage(String brokerId, long time, String msg) {
        super(brokerId, time, msg);
        this.setMessageType(MessageType.BROKER_ACK_DISCONNECT);
    }

    @Override
    public String toString() {
        return "AckDisConnectMessage{} " + super.toString();
    }
}
