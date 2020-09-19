package com.servicematrix.engine.graph;

import com.servicematrix.engine.model.DoorToDoorNode;

import java.util.*;

public class Dijkstra {
    Set<DoorToDoorNode> open = new HashSet<>();
    Set<DoorToDoorNode> close = new HashSet<>();
    Map<String, Integer> path = new HashMap<>();//封装路径距离
    Map<String, String> pathInfo = new HashMap<>();//封装路径信息

    public DoorToDoorNode init(String startNode,Map<String,DoorToDoorNode> doorToDoorNodeMap) {
        doorToDoorNodeMap.forEach((key,value)->{
            if(doorToDoorNodeMap.get(startNode).getChild().containsKey(value)){
                doorToDoorNodeMap.get(startNode).getChild().forEach((key1,value1)->{
                    path.put(key1.getName(),value1);
                    pathInfo.put(key1.getName(),startNode+"->"+key1.getName());
                });
            }else{
                path.put(value.getName(),Integer.MAX_VALUE);
                pathInfo.put(value.getName(),startNode);
            }
        });
        //将初始节点放入close,其他节点放入open
        DoorToDoorNode start = new DoorToDoorGraphBuilder().build(open, close,startNode,doorToDoorNodeMap);
        return start;
    }

    public void computePath(DoorToDoorNode start) {
        //取距离start节点最近的子节点,放入close
        DoorToDoorNode nearest = getShortestPath(start);
        if (nearest == null) {
            return;
        }
        close.add(nearest);     //已遍历的
        open.remove(nearest);   //未遍历的

        Map<DoorToDoorNode, Integer> childs = nearest.getChild();
        for (DoorToDoorNode child : childs.keySet()) {
            if (open.contains(child)) {//如果子节点在open中
                Integer newCompute = path.get(nearest.getName()) + childs.get(child);
                if (newCompute < path.get(child.getName())) {//新计算出来的距离小于之前设置的距离
                    path.put(child.getName(), newCompute);
                    pathInfo.put(child.getName(), pathInfo.get(nearest.getName()) + "->" + child.getName());
                }
            }
        }
        computePath(start);//重复执行自己,确保所有子节点被遍历
        computePath(nearest);//向外一层层递归,直至所有顶点被遍历
    }

    public Integer getPathDistance(String endNode){
        Set<Map.Entry<String, Integer>> paths = path.entrySet();
        for(Map.Entry<String,Integer> path0:paths){
            if(path0.getKey().equals(endNode)){
                return path0.getValue();
            }

        }
        return null;
    }

    public String getPathInfo(String endNode){
        Set<Map.Entry<String, String>> pathInfos = pathInfo.entrySet();
        for (Map.Entry<String, String> pathInfo : pathInfos) {
            if(pathInfo.getKey().equals(endNode))
                return pathInfo.getValue();
        }
        return null;
    }
    public void printPathInfo() {
        Set<Map.Entry<String, String>> pathInfos = pathInfo.entrySet();
        for (Map.Entry<String, String> pathInfo : pathInfos) {
            System.out.println(pathInfo.getKey() + ":" + pathInfo.getValue());
        }
        path.forEach((key,value)-> System.out.println(key+" "+value));
    }

    /**
     * 获取与node最近的子节点
     */
    private DoorToDoorNode getShortestPath(DoorToDoorNode doorToDoorNode) {
        DoorToDoorNode res = null;
        int minDis = Integer.MAX_VALUE;
        Map<DoorToDoorNode, Integer> childs = doorToDoorNode.getChild();
        // 遍历与Node相连接的所有节点，选取其中距离最短的节点
        for (DoorToDoorNode child : childs.keySet()) {
            if (open.contains(child)) {
                int distance = childs.get(child);
                if (distance < minDis) {
                    minDis = distance;
                    res = child;
                }
            }
        }
        return res;
    }

}