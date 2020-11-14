package com.servicematrix.matrixmq.spaceMappingEngine.staticLocation;



import com.servicematrix.matrixmq.spaceMappingEngine.model.Access;
import com.servicematrix.matrixmq.spaceMappingEngine.model.Cell;
import com.servicematrix.matrixmq.spaceMappingEngine.model.CellToCellNode;
import com.servicematrix.matrixmq.spaceMappingEngine.model.Floor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellToCellMapBuilder {
    public Map<String, CellToCellNode> bulid(Floor floor){
        List<Cell> cellList = floor.getCellList();
        Map<String, CellToCellNode> interLayerNodeMap = new HashMap<>();
        for(Cell cell:cellList){
            interLayerNodeMap.put(cell.getCellName(),new CellToCellNode(cell.getCellName()));
        }
        for(int i=0;i<cellList.size();i++){
            Cell cell = cellList.get(i);
            CellToCellNode cellToCellNode = interLayerNodeMap.get(cell.getCellName());
            List<Access> accesses = cell.getAccesses();
            for(Access access : accesses){
                for (Cell value : cellList) {
                    if (value.getAccesses().contains(access)){
                        if(!value.equals(cell))
                            cellToCellNode.getChilds().add(interLayerNodeMap.get(value.getCellName()));
                    }

                }
            }
        }
        return interLayerNodeMap;
    }
}
