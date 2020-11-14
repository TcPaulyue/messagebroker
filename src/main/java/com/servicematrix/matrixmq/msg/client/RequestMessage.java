package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.BaseMessage;

public abstract class RequestMessage extends BaseMessage {
    private RequestHeader requestHeader;
    private RequestBody requestBody;

    public RequestMessage(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public RequestMessage() {
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "requestHeader=" + requestHeader.toString() +
                ", requestBody=" + requestBody.toString() +
                "} " + super.toString();
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
