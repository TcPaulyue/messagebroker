package com.servicematrix.matrixmq.spaceMappingEngine.algorithm;


import com.servicematrix.matrixmq.spaceMappingEngine.model.AccessToAccessNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dijkstra {
    Set<AccessToAccessNode> open = new HashSet<>();
    Set<AccessToAccessNode> close = new HashSet<>();
    Map<String, Double> path = new HashMap<>();//封装路径距离
    Map<String, String> pathInfo = new HashMap<>();//封装路径信息

    public AccessToAccessNode init(String startNode, Map<String, AccessToAccessNode> accessToAccessMap) {
        accessToAccessMap.forEach((key,value)->{
            if(accessToAccessMap.get(startNode).getChild().containsKey(value)){
                accessToAccessMap.get(startNode).getChild().forEach((key1,value1)->{
                    path.put(key1.getName(),value1);
                    pathInfo.put(key1.getName(),startNode+"->"+key1.getName());
                });
            }else{
                path.put(value.getName(),Double.MAX_VALUE);
                pathInfo.put(value.getName(),startNode);
            }
        });
        //将初始节点放入close,其他节点放入open
        accessToAccessMap.forEach((key,value)->{
            if(key.equals(startNode))
                close.add(value);
            else open.add(value);
        });
        return accessToAccessMap.get(startNode);
    }


    public void computePath(AccessToAccessNode start) {
        //取距离start节点最近的子节点,放入close
        AccessToAccessNode nearest = getShortestPath(start);
        if (nearest == null) {
            return;
        }
        close.add(nearest);     //已遍历的
        open.remove(nearest);   //未遍历的

        Map<AccessToAccessNode, Double> childs = nearest.getChild();
        for (AccessToAccessNode child : childs.keySet()) {
            if (open.contains(child)) {//如果子节点在open中
                Double newCompute = path.get(nearest.getName()) + childs.get(child);
                if (newCompute < path.get(child.getName())) {//新计算出来的距离小于之前设置的距离
                    path.put(child.getName(), newCompute);
                    pathInfo.put(child.getName(), pathInfo.get(nearest.getName()) + "->" + child.getName());
                }
            }
        }
        computePath(start);//重复执行自己,确保所有子节点被遍历
        computePath(nearest);//向外一层层递归,直至所有顶点被遍历
    }

    public Double getPathDistance(String endNode){
        Set<Map.Entry<String, Double>> paths = path.entrySet();
        for(Map.Entry<String,Double> path0:paths){
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
    private AccessToAccessNode getShortestPath(AccessToAccessNode accessToAccessNode) {
        AccessToAccessNode res = null;
        double minDis = Integer.MAX_VALUE;
        Map<AccessToAccessNode, Double> childs = accessToAccessNode.getChild();
        // 遍历与Node相连接的所有节点，选取其中距离最短的节点
        for (AccessToAccessNode child : childs.keySet()) {
            if (open.contains(child)) {
                double distance = childs.get(child);
                if (distance < minDis) {
                    minDis = distance;
                    res = child;
                }
            }
        }
        return res;
    }

}