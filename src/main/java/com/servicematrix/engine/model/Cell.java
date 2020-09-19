package com.servicematrix.engine.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private String cellName;

    private List<Door> doors = new ArrayList<>();

    private List<String> mcs = new ArrayList<>();

    public Cell(String cellName) {
        this.cellName = cellName;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public List<String> getMcs() {
        return mcs;
    }

    public void setMcs(List<String> mcs) {
        this.mcs = mcs;
    }
}
