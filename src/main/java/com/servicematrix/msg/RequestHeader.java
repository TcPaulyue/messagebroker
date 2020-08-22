package com.servicematrix.msg;
import java.text.SimpleDateFormat;

public class RequestHeader {
    private long time;

    private RequestMessageType messageType;

    private Location location;

    private String destination;

    public RequestHeader(long time, Location location, String destination) {
        this.time = time;
        this.location = location;
        this.destination = destination;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public RequestMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(RequestMessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", messageType=" + messageType +
                ", location=" + location.toString() +
                ", destination='" + destination + '\'' +
                '}';
    }
}
