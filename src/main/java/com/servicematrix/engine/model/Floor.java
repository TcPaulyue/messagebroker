package com.servicematrix.engine.model;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private String floorName;

    private List<Cell> cellList = new ArrayList<>();

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

    public List<Door> getDoorsOnFloor(){
        List<Door> doors = new ArrayList<>();
        for(Cell cell:cellList){
            for(Door door:cell.getDoors()){
                if(!doors.contains(door)){
                    doors.add(door);
                }
            }
        }
        return doors;
    }

    public List<String> getMcsOnFloor(){
        List<String> mcs = new ArrayList<>();
        for(Cell cell: cellList){
            for(String s :cell.getMcs()){
                if(!mcs.contains(s)){
                    mcs.add(s);
                }
            }
        }
        return mcs;
    }
}
