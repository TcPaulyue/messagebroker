package com.servicematrix.engine.graph;

import com.servicematrix.engine.model.Door;
import com.servicematrix.engine.model.DoorToDoorNode;
import com.servicematrix.engine.model.Floor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DoorToDoorGraphBuilder {

    Map<String, DoorToDoorNode> bulidGraph(Floor floor){
        Map<String,DoorToDoorNode> doorToDoorNodeMap = new HashMap<>();
        List<Door> doorList = floor.getDoorsOnFloor();
        for(Door door:doorList){
            DoorToDoorNode doorToDoorNode = new DoorToDoorNode(door.getName());
            doorToDoorNodeMap.put(door.getName(),doorToDoorNode);
        }
        floor.getCellList().forEach(cell -> {
            List<Door> doors =cell.getDoors();
            if(doors.size()>=2){
                for(int i = 0;i<doors.size();i++){
                    Door doorA = doors.get(i);
                    for(int  j =0;j<doors.size();j++){
                        Door doorB = doors.get(j);
                        if(!doorA.equals(doorB)){
                            Integer distance = calcDistance(doorA.getName(),doorB.getName());
                            doorToDoorNodeMap.get(doorA.getName()).getChild().put(doorToDoorNodeMap.get(doorB.getName()),distance);
                        }
                    }
                }
            }
        });
        return doorToDoorNodeMap;
    }


    private Integer calcDistance(String A, String B){
        if(A.equals("d1")&&B.equals("d3")||A.equals("d3")&&B.equals("d1")
            ||A.equals("d2")&&B.equals("d3")||A.equals("d3")&&B.equals("d2")
            ||A.equals("d3")&&B.equals("v7")||A.equals("v7")&&B.equals("d3")
            ||A.equals("d5")&&B.equals("v7")||A.equals("v7")&&B.equals("d5")
            ||A.equals("d5")&&B.equals("v8")||A.equals("v8")&&B.equals("d5"))
            return 4;
        else if(A.equals("d2")&&B.equals("v7")||A.equals("v7")&&B.equals("d2")
            ||A.equals("v7")&&B.equals("v8")||A.equals("v8")&&B.equals("v7")
            ||A.equals("d4")&&B.equals("v7")||A.equals("v7")&&B.equals("d4"))
            return 3;
        else if(A.equals("d3")&&B.equals("d4")||A.equals("d4")&&B.equals("d3")
            ||A.equals("d1")&&B.equals("d2")||A.equals("d2")&&B.equals("d1"))
            return 5;
        else if(A.equals("d5")&&B.equals("d6")||A.equals("d6")&&B.equals("d5"))
            return 2;
        else if(A.equals("d1")&&B.equals("v7")||A.equals("v7")&&B.equals("d1")
            ||A.equals("d4")&&B.equals("v8")||A.equals("v8")&&B.equals("d4"))
            return 7;
        else if(A.equals("d4")&&B.equals("d5")||A.equals("d5")&&B.equals("d4"))
            return 6;
        else return 6;
    }

    DoorToDoorNode build(Set<DoorToDoorNode> open, Set<DoorToDoorNode> close, String startNode, Map<String, DoorToDoorNode> doorToDoorNodeMap){
        doorToDoorNodeMap.forEach((key,value)->{
            if(key.equals(startNode))
                close.add(value);
            else open.add(value);
        });
        return doorToDoorNodeMap.get(startNode);
    }
}
