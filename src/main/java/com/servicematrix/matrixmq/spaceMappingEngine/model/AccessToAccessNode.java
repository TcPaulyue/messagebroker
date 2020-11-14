package com.servicematrix.matrixmq.spaceMappingEngine.model;

import java.util.HashMap;
import java.util.Map;

public class AccessToAccessNode {
    private String name;

    private Map<AccessToAccessNode,Double> child=new HashMap<>();

    public AccessToAccessNode(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<AccessToAccessNode, Double> getChild() {
        return child;
    }

    public void setChild(Map<AccessToAccessNode, Double> child) {
        this.child = child;
    }
}