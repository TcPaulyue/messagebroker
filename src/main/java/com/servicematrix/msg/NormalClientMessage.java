package com.servicematrix.msg;

public class NormalClientMessage extends ClientMessage {
    private String destination;

    private String message;


    public NormalClientMessage(String id, long time, Location location) {
        super(id, time, location);
    }

    public NormalClientMessage(String id, long time, Location location, String destination) {
        super(id, time, location);
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "NormalClientMessage{" +
                "destination='" + destination + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }

    @Override
    String showMessage() {
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

}
