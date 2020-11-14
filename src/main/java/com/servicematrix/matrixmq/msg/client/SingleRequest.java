package com.servicematrix.matrixmq.msg.client;

public class SingleRequest extends RequestMessage {
    private String destinationId;

    public SingleRequest(RequestHeader requestHeader, RequestBody requestBody, String destinationId) {
        super(requestHeader, requestBody);
        this.destinationId = destinationId;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public RequestHeader getRequestHeader() {
        return super.getRequestHeader();
    }

    @Override
    public void setRequestHeader(RequestHeader requestHeader) {
        super.setRequestHeader(requestHeader);
    }

    @Override
    public RequestBody getRequestBody() {
        return super.getRequestBody();
    }

    @Override
    public void setRequestBody(RequestBody requestBody) {
        super.setRequestBody(requestBody);
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
}
