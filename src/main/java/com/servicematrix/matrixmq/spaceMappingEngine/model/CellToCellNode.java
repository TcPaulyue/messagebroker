package com.servicematrix.matrixmq.spaceMappingEngine.model;


import java.util.ArrayList;
import java.util.List;

public class CellToCellNode {
    private String name;
    private List<CellToCellNode> childs = new ArrayList<>();

    public CellToCellNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CellToCellNode> getChilds() {
        return childs;
    }

    public void setChilds(List<CellToCellNode> childs) {
        this.childs = childs;
    }
}
