package com.servicematrix.matrixmq.msg.client;


import com.servicematrix.matrixmq.msg.MessageType;

public class PublishMessage extends AppContextMessage {

    public PublishMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader,requestBody);
        this.setMessageType(MessageType.PUBLISH);
    }

    public PublishMessage() {
        this.setMessageType(MessageType.PUBLISH);
    }

    @Override
    public String toString() {
        return "Request " + super.toString();
    }
}
