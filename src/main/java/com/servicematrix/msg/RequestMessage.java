package com.servicematrix.msg;

public class RequestMessage {
    private String key;

    private RequestHeader requestHeader;

    private RequestBody requestBody;

    public RequestMessage(RequestHeader requestHeader, RequestBody requestBody) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public RequestMessage(String key, RequestHeader requestHeader, RequestBody requestBody) {
        this.key = key;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "RequestMessage{" +
                "requestHeader=" + requestHeader.toString() +
                ", requestBody=" + requestBody.toString() +
                '}';
    }
}
