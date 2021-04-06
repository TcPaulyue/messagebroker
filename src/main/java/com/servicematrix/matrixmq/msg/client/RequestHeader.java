package com.servicematrix.matrixmq.msg.client;

import java.text.SimpleDateFormat;

public class RequestHeader {
    private String msgId;

    private long time;

    private Location location;

    public RequestHeader(long time, Location location) {
        this.time = time;
        this.location = location;
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


    @Override
    public String toString() {
        return "RequestHeader{" +
                "msgId='" + msgId + '\'' +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", location=" + location.toString() +
                '}';
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
