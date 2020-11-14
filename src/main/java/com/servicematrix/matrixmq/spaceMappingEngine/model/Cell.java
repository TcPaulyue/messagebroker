package com.servicematrix.matrixmq.spaceMappingEngine.model;


import java.util.ArrayList;
import java.util.List;

public class Cell {
    private String cellName;

    private List<Access> accesses = new ArrayList<>();

    private List<Device> devices = new ArrayList<>();

    public Cell(String cellName) {
        this.cellName = cellName;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}
