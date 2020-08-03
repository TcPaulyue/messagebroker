package com.servicematrix.client;

import com.servicematrix.msg.ClientMessage;
import com.servicematrix.msg.Location;
import com.servicematrix.msg.ClientMessageFactory;

public class personProxy {


    private MessageHandler messageHandler;

    private String id;

    private ClientMessage clientMessage;


    public personProxy(MessageHandler messageHandler,String id) {
        this.messageHandler = messageHandler;
        this.id = id;
    }

    public void login(){
        Location location = new Location(1.00,2.00,3.00);
        long time = System.currentTimeMillis();
        messageHandler.login(id,time,location);
    }

    public void sendMessage(){
        try {
            messageHandler.sendMessage(id,System.currentTimeMillis()
                    , new Location(2.00,3.00,3.00)
                    ,"i want a cup of coffee"
                    ,"coffeeMachine");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        messageHandler.logout(id,System.currentTimeMillis()
                ,new Location(1.00,2.00,3.00));
    }

    public static void main(String[] args) throws Exception {
        MessageHandler messageHandler = new MessageHandler(new ClientMessageFactory());
        personProxy personProxy = new personProxy(messageHandler,"person02");
        messageHandler.init("localhost",8080);
        personProxy.login();
        Thread.sleep(1000);
        personProxy.sendMessage();
//        Thread.sleep(1000);
//        personProxy.logout();


    }
}
