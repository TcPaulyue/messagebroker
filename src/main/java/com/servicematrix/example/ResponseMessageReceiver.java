package com.servicematrix.example;

import com.servicematrix.client.netty.ServerMessageHandler;

import com.servicematrix.msg.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 继承ServerMessageHandler类，用来接收mq发送来的消息
 * 其中有一个channel变量，调用channel.writeAndFlush()方法可以向mq发送答复消息
 * responseMessage()对应于sendMessage()方法
 * ackMessage()对应于bind()和unbind()方法
 */
public class ResponseMessageReceiver extends ServerMessageHandler {

    public static List<String> messageList = new ArrayList<>();

    @Override
    public void responseMessage(RequestMessage requestMessage) throws InterruptedException {
        System.out.println(requestMessage.toString());
        messageList.add(requestMessage.toString());
     //   this.channel.writeAndFlush("abcde");  //回复mq
    }

    @Override
    public void ackMessage(String msg) {
        System.out.println(msg);
        messageList.add(msg);

    }
}
