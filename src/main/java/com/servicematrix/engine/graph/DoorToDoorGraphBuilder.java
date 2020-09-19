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

//        DoorToDoorNode doorToDoorNodeD1 =new DoorToDoorNode("d1");
//        DoorToDoorNode doorToDoorNodeD2 =new DoorToDoorNode("d2");
//        DoorToDoorNode doorToDoorNodeD3 =new DoorToDoorNode("d3");
//        DoorToDoorNode doorToDoorNodeD4 =new DoorToDoorNode("d4");
//        DoorToDoorNode doorToDoorNodeD5 =new DoorToDoorNode("d5");
//        DoorToDoorNode doorToDoorNodeD6 =new DoorToDoorNode("d6");
//        DoorToDoorNode doorToDoorNodeV7 =new DoorToDoorNode("v7");
//        DoorToDoorNode doorToDoorNodeV8 =new DoorToDoorNode("v8");
//
//        doorToDoorNodeD1.getChild().put(doorToDoorNodeD2,5);
//        doorToDoorNodeD1.getChild().put(doorToDoorNodeD3,4);
//        doorToDoorNodeD1.getChild().put(doorToDoorNodeV7,7);
//
//        doorToDoorNodeD2.getChild().put(doorToDoorNodeD1,5);
//        doorToDoorNodeD2.getChild().put(doorToDoorNodeD3,4);
//        doorToDoorNodeD2.getChild().put(doorToDoorNodeV7,3);
//
//        doorToDoorNodeD3.getChild().put(doorToDoorNodeD1,4);
//        doorToDoorNodeD3.getChild().put(doorToDoorNodeD2,4);
//        doorToDoorNodeD3.getChild().put(doorToDoorNodeD4,5);
//        doorToDoorNodeD3.getChild().put(doorToDoorNodeV7,4);
//
//        doorToDoorNodeD4.getChild().put(doorToDoorNodeD3,5);
//        doorToDoorNodeD4.getChild().put(doorToDoorNodeV7,3);
//        doorToDoorNodeD4.getChild().put(doorToDoorNodeD5,6);
//        doorToDoorNodeD4.getChild().put(doorToDoorNodeV8,7);
//
//        doorToDoorNodeD5.getChild().put(doorToDoorNodeD4,6);
//        doorToDoorNodeD5.getChild().put(doorToDoorNodeV7,4);
//        doorToDoorNodeD5.getChild().put(doorToDoorNodeV8,4);
//        doorToDoorNodeD5.getChild().put(doorToDoorNodeD6,2);
//
//        doorToDoorNodeD6.getChild().put(doorToDoorNodeD5,2);
//
//        doorToDoorNodeV7.getChild().put(doorToDoorNodeD1,7);
//        doorToDoorNodeV7.getChild().put(doorToDoorNodeD2,3);
//        doorToDoorNodeV7.getChild().put(doorToDoorNodeD3,4);
//        doorToDoorNodeV7.getChild().put(doorToDoorNodeD4,3);
//        doorToDoorNodeV7.getChild().put(doorToDoorNodeD5,4);
//        doorToDoorNodeV7.getChild().put(doorToDoorNodeV8,3);
//
//        doorToDoorNodeV8.getChild().put(doorToDoorNodeV7,3);

//        open.add(doorToDoorNodeD2);
//        open.add(doorToDoorNodeD1);
//        open.add(doorToDoorNodeD4);
//        open.add(doorToDoorNodeD5);
//        open.add(doorToDoorNodeD6);
//        open.add(doorToDoorNodeV7);
//        open.add(doorToDoorNodeV8);
//        close.add(doorToDoorNodeD3);
//        return doorToDoorNodeD3;
    }
}
