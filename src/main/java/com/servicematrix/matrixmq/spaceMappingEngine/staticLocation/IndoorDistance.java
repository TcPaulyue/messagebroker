package com.servicematrix.matrixmq.spaceMappingEngine.staticLocation;



import com.servicematrix.matrixmq.msg.client.Location;
import com.servicematrix.matrixmq.spaceMappingEngine.algorithm.Dijkstra;
import com.servicematrix.matrixmq.spaceMappingEngine.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IndoorDistance {
    private static Map<String, CellToCellNode> interLayerNodeMap = new HashMap<>();
    private static Map<String, DeviceToAccessNode> pointToDoorNodeMap = new HashMap<>();
    private static Map<String, AccessToAccessNode> doorToDoorNodeMap = new HashMap<>();
    public static Map<Access,Map<Access,Double>> minDistanceMap = new HashMap<>();


    public void init(Floor floor) {
        interLayerNodeMap = new CellToCellMapBuilder().bulid(floor);
        pointToDoorNodeMap = new DeviceToAccessMapBuilder().build(floor);
        doorToDoorNodeMap = new AccessToAccessMapBuilder().bulidGraph(floor);
        List<Access> accesses = floor.getDoorsOnFloor();
        accesses.forEach(access -> {
            Map<Access,Double> map = new HashMap<>();
            accesses.forEach(access1 -> map.put(access1,0.0));
            minDistanceMap.put(access,map);
        });
    }

    public void buildMinDistanceMap(){
        if(doorToDoorNodeMap != null){
            minDistanceMap.keySet().forEach(start->{
                Map<Access,Double> endMap = minDistanceMap.get(start);
                endMap.keySet().forEach(end->{
                    if(start == end)
                        minDistanceMap.get(start).put(end,0.0);
                    else{
                        Dijkstra dijkstra = new Dijkstra();
                        AccessToAccessNode begin = dijkstra.init(start.getName(),doorToDoorNodeMap);
                        dijkstra.computePath(begin);
                        minDistanceMap.get(start).put(end,dijkstra.getPathDistance(end.getName()));
                    }
                });
            });
        }
        System.out.println(minDistanceMap);
    }

    public static Map<Access, Map<Access, Double>> getMinDistanceMap() {
        return minDistanceMap;
    }

    public static void setMinDistanceMap(Map<Access, Map<Access, Double>> minDistanceMap) {
        IndoorDistance.minDistanceMap = minDistanceMap;
    }

    public Map<String, CellToCellNode> getInterLayerNodeMap() {
        return interLayerNodeMap;
    }

    public void setInterLayerNodeMap(Map<String, CellToCellNode> interLayerNodeMap) {
        IndoorDistance.interLayerNodeMap = interLayerNodeMap;
    }

    public Map<String, DeviceToAccessNode> getPointToDoorNodeMap() {
        return pointToDoorNodeMap;
    }

    public void setPointToDoorNodeMap(Map<String, DeviceToAccessNode> pointToDoorNodeMap) {
        IndoorDistance.pointToDoorNodeMap = pointToDoorNodeMap;
    }

    public Map<String, AccessToAccessNode> getDoorToDoorNodeMap() {
        return doorToDoorNodeMap;
    }

    public void setDoorToDoorNodeMap(Map<String, AccessToAccessNode> doorToDoorNodeMap) {
        IndoorDistance.doorToDoorNodeMap = doorToDoorNodeMap;
    }

    public static void main(String[] args) {
        Access accessd1 = new Access("d1");
        accessd1.setLocation(new Location(1.0,4.0,0.0));
        Access accessd2 = new Access("d2");
        accessd2.setLocation(new Location(3.0,4.0,0.0));
        Access accessd3 = new Access("d3");
        accessd3.setLocation(new Location(2.0,2.0,0.0));
        Access accessd4 = new Access("d4");
        accessd4.setLocation(new Location(4.0,1.0,0.0));
        Access accessd5 = new Access("d5");
        accessd5.setLocation(new Location(6.0,1.0,0.0));
        Access accessd6 = new Access("d6");
        accessd6.setLocation(new Location(7.0,4.0,0.0));
        Access accessv7 = new Access("v7");
        accessv7.setLocation(new Location(4.0,3.0,0.0));
        Access accessv8 = new Access("v8");
        accessv8.setLocation(new Location(5.0,4.0,0.0));

        Floor floor = new Floor("floor1",1,3);
        Cell cell = new Cell("R1");
        cell.getAccesses().add(accessd1);
        floor.getCellList().add(cell);
        cell = new Cell("R2");
        cell.getAccesses().add(accessd2);
        floor.getCellList().add(cell);
        cell = new Cell("C3");
        cell.getAccesses().add(accessv8);
        floor.getCellList().add(cell);
        cell = new Cell("C4");
        cell.getAccesses().add(accessd4);
        cell.getAccesses().add(accessv7);
        cell.getAccesses().add(accessv8);
        cell.getAccesses().add(accessd5);
        floor.getCellList().add(cell);

        cell = new Cell("C5");
        cell.getAccesses().add(accessd1);
        cell.getAccesses().add(accessd2);
        cell.getAccesses().add(accessd3);
        cell.getAccesses().add(accessv7);
        floor.getCellList().add(cell);

        cell = new Cell("R6");
        cell.getAccesses().add(accessd3);
        cell.getAccesses().add(accessd4);
        floor.getCellList().add(cell);

        cell = new Cell("R7");
        cell.getAccesses().add(accessd5);
        cell.getAccesses().add(accessd6);
        floor.getCellList().add(cell);

        cell = new Cell("R8");
        cell.getAccesses().add(accessd6);
        floor.getCellList().add(cell);

        IndoorDistance indoorDistance = new IndoorDistance();
        indoorDistance.init(floor);
        //cell直接的连通图
        CellToCellMapBuilder cellToCellMapBuilder = new CellToCellMapBuilder();
        Map<String, CellToCellNode> interLayerNodeMap = cellToCellMapBuilder.bulid(floor);

        //cell中的智能设备和该cell的每个出口之间的距离map
        DeviceToAccessMapBuilder deviceToAccessMapBuilder = new DeviceToAccessMapBuilder();
        Map<String, DeviceToAccessNode> pointToDoorNodeMap = deviceToAccessMapBuilder.build(floor);

        //该floor中的出口之间的带权连通图
        AccessToAccessMapBuilder accessToAccessMapBuilder = new AccessToAccessMapBuilder();
        Map<String, AccessToAccessNode> doorToDoorNodeMap = accessToAccessMapBuilder.bulidGraph(floor);

        indoorDistance.buildMinDistanceMap();
//        //起点和终点
//        String startMc = "paul";
//        String endMc = "coffeeMachine01";
//
//        Set<Access> startAccesses = pointToDoorNodeMap.get(startMc).getDoorInfo().keySet();
//        Set<Access> endAccesses = pointToDoorNodeMap.get(endMc).getDoorInfo().keySet();

//        startAccesses.forEach(startDoor -> endAccesses.forEach(endDoor -> {
//            Dijkstra test = new Dijkstra();
//            AccessToAccessNode start = test.init(startDoor.getName(), doorToDoorNodeMap);
//            test.computePath(start);
//            System.out.println(startDoor.getName() + "->" + endDoor.getName() + "  distance: " + test.getPathDistance(endDoor.getName())
//                    + "  path: " + test.getPathInfo(endDoor.getName()));
//        }));

    }
}
