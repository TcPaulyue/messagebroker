package com.servicematrix.engine.model;

import java.util.HashMap;
import java.util.Map;

public class PointToDoorNode {
    private String name;
    private Map<Door,Integer> doorInfo = new HashMap<>();

    public PointToDoorNode(String name) {
        this.name = name;
    }

    public Map<Door, Integer> getDoorInfo() {
        return doorInfo;
    }

    public void setDoorInfo(Map<Door, Integer> doorInfo) {
        this.doorInfo = doorInfo;
    }
}
