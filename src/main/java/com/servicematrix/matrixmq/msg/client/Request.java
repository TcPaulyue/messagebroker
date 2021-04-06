package com.servicematrix.matrixmq.msg.client;


import com.servicematrix.matrixmq.msg.MessageType;

public class Request extends AppContextMessage {

    public Request(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader,requestBody);
        this.setMessageType(MessageType.REQUEST);
    }

    public Request() {
        this.setMessageType(MessageType.REQUEST);
    }

    @Override
    public String toString() {
        return "Request " + super.toString();
    }
}
