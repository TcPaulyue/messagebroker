package com.servicematrix.matrixmq.msg.client;

import com.servicematrix.matrixmq.msg.MessageType;

import java.util.ArrayList;
import java.util.List;

public class BroadCastRequest extends RequestMessage {
    private List<String> topics = new ArrayList<>();

    public BroadCastRequest(RequestHeader requestHeader, RequestBody requestBody) {
        super(requestHeader, requestBody);
        this.setMessageType(MessageType.BROADCAST);
    }

    public BroadCastRequest(RequestHeader requestHeader, RequestBody requestBody, List<String> topics) {
        super(requestHeader, requestBody);
        this.topics = topics;
        this.setMessageType(MessageType.BROADCAST);
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "BroadCastRequest{" +
                "topics=" + topics +
                "} " + super.toString();
    }
}
