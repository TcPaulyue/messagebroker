package com.servicematrix.matrixmq.msg.broker;

import com.servicematrix.matrixmq.msg.MessageType;

public class AckBindMessage extends BrokerMessage {

    public AckBindMessage(String brokerId, long time, String msg) {
        super(brokerId, time, msg);
        this.setMessageType(MessageType.BROKER_ACK_BIND);
    }

    public AckBindMessage() {
        this.setMessageType(MessageType.BROKER_ACK_BIND);
    }

    @Override
    public String toString() {
        return "AckBindMessage{} " + super.toString();
    }
}
