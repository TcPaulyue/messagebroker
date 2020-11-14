package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;

public class BindMessage extends RequestMessage {
    public BindMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.BIND);
    }

    public BindMessage() {
        this.setMessageType(MessageType.BIND);
    }

    @Override
    public String toString() {
        return "BindMessage{} " + super.toString();
    }
}
