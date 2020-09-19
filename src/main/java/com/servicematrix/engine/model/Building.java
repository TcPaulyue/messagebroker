package com.servicematrix.engine.model;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private String buildingName;

    private List<Floor> floorList = new ArrayList<>();

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public List<Floor> getFloorList() {
        return floorList;
    }

    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList;
    }


}
