package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;
import io.netty.channel.ChannelId;

public class AppContextMessage extends RequestMessage {
    private String AppContextId;

    public AppContextMessage(RequestHeader requestHeader, RequestBody requestBody, String appContextId) {
        super(requestHeader, requestBody);
        AppContextId = appContextId;
    }

    public AppContextMessage(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.APPLICATIONCONTEXT);
    }

    public AppContextMessage() {
        this.setMessageType(MessageType.APPLICATIONCONTEXT);
    }



    public String getAppContextId() {
        return AppContextId;
    }

    public void setAppContextId(String appContextId) {
        AppContextId = appContextId;
    }

    @Override
    public String toString() {
        return "{" +
                "AppContextId='" + AppContextId + '\'' +
                "} " + super.toString();
    }
}
