package com.servicematrix.msg;

import java.util.Map;

public class RequestAccessibleMessage extends RequestBody {
    private Map<String, Map<String,Boolean>> accessibleMap;

    public RequestAccessibleMessage(Map<String, Map<String, Boolean>> accessibleMap) {
        super();
        this.accessibleMap = accessibleMap;
    }

    public Map<String, Map<String, Boolean>> getAccessibleMap() {
        return accessibleMap;
    }

    public void setAccessibleMap(Map<String, Map<String, Boolean>> accessibleMap) {
        this.accessibleMap = accessibleMap;
    }
}
