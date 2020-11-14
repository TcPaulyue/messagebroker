package com.servicematrix.matrixmq.spaceMappingEngine.model;


import com.servicematrix.matrixmq.msg.client.Location;

public class Access {
    private String name;
    private Location location;

    public Access(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Access() {
    }

    public Access(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
