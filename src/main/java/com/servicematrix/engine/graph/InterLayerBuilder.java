package com.servicematrix.engine.graph;

import com.servicematrix.engine.model.Cell;
import com.servicematrix.engine.model.Door;
import com.servicematrix.engine.model.Floor;
import com.servicematrix.engine.model.InterLayerNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterLayerBuilder {
    public Map<String,InterLayerNode> bulid(Floor floor){
        List<Cell> cellList = floor.getCellList();
        Map<String,InterLayerNode> interLayerNodeMap = new HashMap<>();
        for(Cell cell:cellList){
            interLayerNodeMap.put(cell.getCellName(),new InterLayerNode(cell.getCellName()));
        }
        for(int i=0;i<cellList.size();i++){
            Cell cell = cellList.get(i);
            InterLayerNode interLayerNode = interLayerNodeMap.get(cell.getCellName());
            List<Door> doors = cell.getDoors();
            for(Door door:doors){
                for (Cell value : cellList) {
                    if (value.getDoors().contains(door)){
                        if(!value.equals(cell))
                            interLayerNode.getChilds().add(interLayerNodeMap.get(value.getCellName()));
                    }

                }
            }
        }
        return interLayerNodeMap;
    }
}
