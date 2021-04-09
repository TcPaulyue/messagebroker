package com.servicematrix.matrixmq.msg.broker;

import com.servicematrix.matrixmq.msg.MessageType;

public class AckConnectMessage extends BrokerMessage {

    public AckConnectMessage(String brokerId, long time, String msg) {
        super(brokerId, time, msg);
        this.setMessageType(MessageType.BROKER_ACK_CONNECT);
    }

    public AckConnectMessage() {
        this.setMessageType(MessageType.BROKER_ACK_CONNECT);
    }

    @Override
    public String toString() {
        return "AckBindMessage{} " + super.toString();
    }
}
