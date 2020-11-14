package com.servicematrix.matrixmq.msg.client;

import java.text.SimpleDateFormat;

public class RequestHeader {
    private String msgId;

    private long time;

    private Location location;

    private String topic;

    public RequestHeader(long time, Location location, String topic) {
        this.time = time;
        this.location = location;
        this.topic = topic;
    }

    public RequestHeader() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "msgId='" + msgId + '\'' +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", location=" + location.toString() +
                ", topic='" + topic + '\'' +
                '}';
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
