package com.servicematrix.engine.graph;

import com.servicematrix.engine.model.Floor;
import com.servicematrix.engine.model.PointToDoorNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointToDoorBuilder {

    public Map<String, PointToDoorNode> build( Floor floor){
        Map<String,PointToDoorNode> pointToDoorNodeMap = new HashMap<>();
        List<String> mcs = floor.getMcsOnFloor();
        mcs.forEach(mc-> pointToDoorNodeMap.put(mc,new PointToDoorNode(mc)));

        floor.getCellList().forEach(cell -> mcs.forEach(mc->{
            if(cell.getMcs().contains(mc)){
                cell.getDoors().forEach(door -> pointToDoorNodeMap.get(mc).getDoorInfo().put(door,4));
            }
        }));
        return pointToDoorNodeMap;
    }
}
