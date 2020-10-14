package com.servicematrix;

import com.servicematrix.client.ClientMessageSender;
import com.servicematrix.msg.Location;
import com.servicematrix.msg.RequestBody;
import com.servicematrix.msg.RequestHeader;


/**
 * 一个Client端的简单实现
 */
public class DemoClient {
    private ClientMessageSender clientMessageSender;   //需要引入ClientMessageSender来发送消息

    public DemoClient(ClientMessageSender clientMessageSender) {
        this.clientMessageSender = clientMessageSender;
    }

    //向mq发送绑定请求
    void bind(){
        Location location = new Location(1.00,2.00,3.00);
        RequestHeader requestHeader = new RequestHeader(System.currentTimeMillis(),location,"server");
        String message = "bind to mq.";
        RequestBody requestBody = new RequestBody(message.getBytes());
        clientMessageSender.bind("coffeeMachine01",requestHeader,requestBody);
    }

    //向mq发送业务消息
    void sendMessage(){
        Location location = new Location(1.00,2.00,3.00);
        RequestHeader requestHeader = new RequestHeader(System.currentTimeMillis(),location,"paul");
        String message = "i want a cup of coffee";
        RequestBody requestBody = new RequestBody(message.getBytes());
        clientMessageSender.sendMessage(requestHeader,requestBody);
    }

    //和mq解除绑定
    void unbind(){
        Location location = new Location(1.00,2.00,3.00);
        RequestHeader requestHeader = new RequestHeader(System.currentTimeMillis(),location,"server");
        clientMessageSender.unbind("coffeeMachine01",requestHeader,null);
    }

    public static void main(String[] args) throws Exception {

        //创建一个ClientMessageSender实例，其中指定host和port
        //最后一个参数是用来处理接收到的消息
        ClientMessageSender clientMessageSender = new ClientMessageSender("localhost",8082,new ResponseMessageReceiver());

        //新建一个具体的client实例
        DemoClient demoClient = new DemoClient(clientMessageSender);
        //和mq发送绑定请求
        demoClient.bind();

        Thread.sleep(1000);
        //发送一条消息
        demoClient.sendMessage();
        Thread.sleep(1000);
        //解除绑定，不会接收到mq发送的消息
     //   demoClient.unbind();
    }
}
