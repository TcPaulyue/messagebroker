package com.servicematrix.server;

public class ResponseData {
    private int intValue;

    private String msg;
    // standard getters and setters


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }
}