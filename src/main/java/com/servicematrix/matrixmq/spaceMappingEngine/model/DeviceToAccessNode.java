package com.servicematrix.matrixmq.spaceMappingEngine.model;

import java.util.HashMap;
import java.util.Map;

public class DeviceToAccessNode {
    private String serviceName;
    private Map<Access,Double> accessMap = new HashMap<>();

    public DeviceToAccessNode(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<Access, Double> getAccessMap() {
        return accessMap;
    }

    public void setAccessMap(Map<Access, Double> accessMap) {
        this.accessMap = accessMap;
    }

    public String getserviceName() {
        return serviceName;
    }

    public void setserviceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
