package com.servicematrix.engine.model;

import java.util.HashMap;
import java.util.Map;

public class DoorToDoorNode {
    private String name;
    private Map<DoorToDoorNode,Integer> child=new HashMap<>();
    public DoorToDoorNode(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Map<DoorToDoorNode, Integer> getChild() {
        return child;
    }
    public void setChild(Map<DoorToDoorNode, Integer> child) {
        this.child = child;
    }
}