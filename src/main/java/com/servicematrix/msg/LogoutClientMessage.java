package com.servicematrix.msg;

public class LogoutClientMessage extends ClientMessage {
    private String logoutSign;

    public LogoutClientMessage(String id, long time, Location location) {
        super(id, time, location);
    }

    public LogoutClientMessage(String id, long time, Location location, String logoutSign) {
        super(id, time, location);
        this.logoutSign = logoutSign;
    }

    public String getLogoutSign() {
        return logoutSign;
    }

    public void setLogoutSign(String logoutSign) {
        this.logoutSign = logoutSign;
    }

    @Override
    String showMessage() {
        return null;
    }
}
