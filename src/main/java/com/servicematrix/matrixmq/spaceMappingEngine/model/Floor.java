package com.servicematrix.matrixmq.spaceMappingEngine.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private String floorName;

    private Integer level;    //第几层

    private Integer height;   //层高

    private List<Cell> cellList = new ArrayList<>();

    public Floor(String floorName, Integer level, Integer height) {
        this.floorName = floorName;
        this.level = level;
        this.height = height;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public List<Access> getDoorsOnFloor(){
        List<Access> accesses = new ArrayList<>();
        for(Cell cell:cellList){
            for(Access access :cell.getAccesses()){
                if(!accesses.contains(access)){
                    accesses.add(access);
                }
            }
        }
        return accesses;
    }

    public List<Device> getDevicesOnFloor(){
        List<Device> deviceList = new ArrayList<>();
        for(Cell cell :cellList){
            for(Device device:cell.getDevices()){
                if(!deviceList.contains(device)){
                    deviceList.add(device);
                }
            }
        }
        return deviceList;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
