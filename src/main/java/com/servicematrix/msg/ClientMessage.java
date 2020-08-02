package com.servicematrix.msg;


import java.text.SimpleDateFormat;

public abstract class ClientMessage {

    private String id;

    private long time;

    private Location location;


    public ClientMessage(String id, long time, Location location) {
        this.id = id;
        this.time = time;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "id='" + id + '\'' +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", location=" + location.toString() +
                '}';
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

    abstract String showMessage();
}
