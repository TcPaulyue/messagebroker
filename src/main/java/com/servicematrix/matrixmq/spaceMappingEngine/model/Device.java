package com.servicematrix.matrixmq.spaceMappingEngine.model;


import com.servicematrix.matrixmq.msg.client.Location;

public class Device {
    private String id;
    private String serviceName;
    private Location location;

    public Device(String id, String serviceName, Location location) {
        this.id = id;
        this.serviceName = serviceName;
        this.location = location;
    }

    public Device(String serviceName) {
        this.serviceName = serviceName;
    }

    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
