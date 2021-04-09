package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;

public class DisConnectMessage extends RequestMessage {
    public DisConnectMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.DISCONNECT);
    }

    public DisConnectMessage() {
        this.setMessageType(MessageType.DISCONNECT);
    }

    @Override
    public String toString() {
        return "UnBindMessage{} " + super.toString();
    }
}
