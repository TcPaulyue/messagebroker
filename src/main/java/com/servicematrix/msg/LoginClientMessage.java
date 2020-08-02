package com.servicematrix.msg;

public class LoginClientMessage extends ClientMessage {
    private String loginSign;

    public LoginClientMessage(String id, long time, Location location, String loginSign) {
        super(id, time, location);
        this.loginSign = loginSign;
    }

    public LoginClientMessage(String id, long time, Location location) {
        super(id, time, location);
    }


    @Override
    public String showMessage() {
        return loginSign;
    }

    @Override
    public String toString() {
        return "LoginClientMessage{" +
                "loginSign='" + loginSign + '\'' +
                "} " + super.toString();
    }

    public void setLoginSign(String loginSign) {
        this.loginSign = loginSign;
    }

    public String getLoginSign() {
        return loginSign;
    }
}
