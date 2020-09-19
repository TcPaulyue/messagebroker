package com.servicematrix.engine.model;


import java.util.ArrayList;
import java.util.List;

public class InterLayerNode {
    private String name;
    private List<InterLayerNode> childs = new ArrayList<>();

    public InterLayerNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InterLayerNode> getChilds() {
        return childs;
    }

    public void setChilds(List<InterLayerNode> childs) {
        this.childs = childs;
    }
}
