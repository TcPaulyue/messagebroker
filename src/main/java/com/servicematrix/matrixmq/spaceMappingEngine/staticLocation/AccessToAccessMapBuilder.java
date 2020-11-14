package com.servicematrix.matrixmq.spaceMappingEngine.staticLocation;



import com.servicematrix.matrixmq.spaceMappingEngine.model.Access;
import com.servicematrix.matrixmq.spaceMappingEngine.model.AccessToAccessNode;
import com.servicematrix.matrixmq.spaceMappingEngine.model.Floor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccessToAccessMapBuilder {

    Map<String, AccessToAccessNode> bulidGraph(Floor floor){
        Map<String, AccessToAccessNode> doorToDoorNodeMap = new HashMap<>();
        List<Access> accessList = floor.getDoorsOnFloor();
        for(Access access : accessList){
            AccessToAccessNode accessToAccessNode = new AccessToAccessNode(access.getName());
            doorToDoorNodeMap.put(access.getName(), accessToAccessNode);
        }
        floor.getCellList().forEach(cell -> {
            List<Access> accesses =cell.getAccesses();
            if(accesses.size()>=2){
                for(int i = 0; i< accesses.size(); i++){
                    Access accessA = accesses.get(i);
                    for(int j = 0; j< accesses.size(); j++){
                        Access accessB = accesses.get(j);
                        if(!accessA.equals(accessB)){
                            Double distance = calcDistance(accessA,accessB);
                            doorToDoorNodeMap.get(accessA.getName()).getChild().put(doorToDoorNodeMap.get(accessB.getName()),distance);
                        }
                    }
                }
            }
        });
        return doorToDoorNodeMap;
    }


    private Double calcDistance(Access a, Access b){
        return a.getLocation().calcDistance(b.getLocation());
    }

    public AccessToAccessNode build(Set<AccessToAccessNode> open, Set<AccessToAccessNode> close, String startNode, Map<String, AccessToAccessNode> doorToDoorNodeMap){
        doorToDoorNodeMap.forEach((key,value)->{
            if(key.equals(startNode))
                close.add(value);
            else open.add(value);
        });
        return doorToDoorNodeMap.get(startNode);
    }
}
