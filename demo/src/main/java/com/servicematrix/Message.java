package com.servicematrix;

import io.netty.channel.Channel;

public class Message{

    private String id;

    private Integer num;

    private String topic;

    private String msg;

    private Channel channel;

    public Message(String id,Integer num,String topic){
        this.id = id;
        this.num = num;
        this.topic = topic;
    }

    public Message(){}

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void sendMessage(String message){
        this.setMsg(message);
        System.out.println("send Message...");
        channel.writeAndFlush(message);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public String getMsg() {
        return msg;
    }

    public String getTopic() {
        return topic;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public static void main(String[] args) throws Exception {
        MessageConnectFactory messageConnectFactory = new MessageConnectFactory();
        messageConnectFactory.init();
        Message message = new Message("message1",1,"coffeeMachine");
        message.setChannel(messageConnectFactory.getMessageChannel());
        message.sendMessage("i want a cup of coffee");
    }
}
