package com.servicematrix.engine.graph;

import com.servicematrix.engine.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class IndoorDistance {
    private static Map<String, InterLayerNode> interLayerNodeMap = new HashMap<>();
    private static Map<String, PointToDoorNode> pointToDoorNodeMap = new HashMap<>();
    private static Map<String, DoorToDoorNode> doorToDoorNodeMap = new HashMap<>();

    public void init(Floor floor) {
        interLayerNodeMap = new InterLayerBuilder().bulid(floor);
        pointToDoorNodeMap = new PointToDoorBuilder().build(floor);
        doorToDoorNodeMap = new DoorToDoorGraphBuilder().bulidGraph(floor);
    }


    public Map<String, InterLayerNode> getInterLayerNodeMap() {
        return interLayerNodeMap;
    }

    public void setInterLayerNodeMap(Map<String, InterLayerNode> interLayerNodeMap) {
        IndoorDistance.interLayerNodeMap = interLayerNodeMap;
    }

    public Map<String, PointToDoorNode> getPointToDoorNodeMap() {
        return pointToDoorNodeMap;
    }

    public void setPointToDoorNodeMap(Map<String, PointToDoorNode> pointToDoorNodeMap) {
        IndoorDistance.pointToDoorNodeMap = pointToDoorNodeMap;
    }

    public Map<String, DoorToDoorNode> getDoorToDoorNodeMap() {
        return doorToDoorNodeMap;
    }

    public void setDoorToDoorNodeMap(Map<String, DoorToDoorNode> doorToDoorNodeMap) {
        IndoorDistance.doorToDoorNodeMap = doorToDoorNodeMap;
    }




    public static void main(String[] args) {
        Door doord1 = new Door("d1");
        Door doord2 = new Door("d2");
        Door doord3 = new Door("d3");
        Door doord4 = new Door("d4");
        Door doord5 = new Door("d5");
        Door doord6 = new Door("d6");
        Door doorv7 = new Door("v7");
        Door doorv8 = new Door("v8");

        String p = "paul";
        String q = "coffeeMachine01";

        Floor floor = new Floor();
        Cell cell = new Cell("R1");
        cell.getDoors().add(doord1);
        floor.getCellList().add(cell);
        cell = new Cell("R2");
        cell.getDoors().add(doord2);
        floor.getCellList().add(cell);
        cell = new Cell("C3");
        cell.getDoors().add(doorv8);
        floor.getCellList().add(cell);
        cell = new Cell("C4");
        cell.getDoors().add(doord4);
        cell.getDoors().add(doorv7);
        cell.getDoors().add(doorv8);
        cell.getDoors().add(doord5);
        floor.getCellList().add(cell);

        cell = new Cell("C5");
        cell.getDoors().add(doord1);
        cell.getDoors().add(doord2);
        cell.getDoors().add(doord3);
        cell.getDoors().add(doorv7);
        floor.getCellList().add(cell);

        cell = new Cell("R6");
        cell.getDoors().add(doord3);
        cell.getDoors().add(doord4);
        cell.getMcs().add(p);
        floor.getCellList().add(cell);

        cell = new Cell("R7");
        cell.getDoors().add(doord5);
        cell.getDoors().add(doord6);
        floor.getCellList().add(cell);

        cell = new Cell("R8");
        cell.getDoors().add(doord6);
        cell.getMcs().add(q);
        floor.getCellList().add(cell);

        IndoorDistance indoorDistance = new IndoorDistance();
        indoorDistance.init(floor);
        //cell直接的连通图
        InterLayerBuilder interLayerBuilder = new InterLayerBuilder();
        Map<String, InterLayerNode> interLayerNodeMap = interLayerBuilder.bulid(floor);

        //cell中的智能设备和该cell的每个出口之间的距离map
        PointToDoorBuilder pointToDoorBuilder = new PointToDoorBuilder();
        Map<String, PointToDoorNode> pointToDoorNodeMap = pointToDoorBuilder.build(floor);

        //该floor中的出口之间的带权连通图
        DoorToDoorGraphBuilder doorToDoorGraphBuilder = new DoorToDoorGraphBuilder();
        Map<String, DoorToDoorNode> doorToDoorNodeMap = doorToDoorGraphBuilder.bulidGraph(floor);

        //起点和终点
        String startMc = "p";
        String endMc = "q";

        Set<Door> startDoors = pointToDoorNodeMap.get(startMc).getDoorInfo().keySet();
        Set<Door> endDoors = pointToDoorNodeMap.get(endMc).getDoorInfo().keySet();

        startDoors.forEach(startDoor -> endDoors.forEach(endDoor -> {
            Dijkstra test = new Dijkstra();
            DoorToDoorNode start = test.init(startDoor.getName(), doorToDoorNodeMap);
            test.computePath(start);
            System.out.println(startDoor.getName() + "->" + endDoor.getName() + "  distance: " + test.getPathDistance(endDoor.getName())
                    + "  path: " + test.getPathInfo(endDoor.getName()));
        }));

    }
}
