package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;

public class UnBindMessage extends RequestMessage {
    public UnBindMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.UNBIND);
    }

    public UnBindMessage() {
        this.setMessageType(MessageType.UNBIND);
    }

    @Override
    public String toString() {
        return "UnBindMessage{} " + super.toString();
    }
}
