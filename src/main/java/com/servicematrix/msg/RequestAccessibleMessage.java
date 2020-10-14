package com.servicematrix.msg;

import java.util.Map;

public class RequestAccessibleMessage extends RequestBody {
    private Map<String, Map<String,Double>> accessibleMap;

    public RequestAccessibleMessage(Map<String, Map<String, Double>> accessibleMap) {
        super();
        this.accessibleMap = accessibleMap;
    }

    public Map<String, Map<String, Double>> getAccessibleMap() {
        return accessibleMap;
    }

    public void setAccessibleMap(Map<String, Map<String, Double>> accessibleMap) {
        this.accessibleMap = accessibleMap;
    }
}
