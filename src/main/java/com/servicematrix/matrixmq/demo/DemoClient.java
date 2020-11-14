package com.servicematrix.matrixmq.demo;

import com.servicematrix.matrixmq.client.MQClient;
import com.servicematrix.matrixmq.msg.client.Location;

public class DemoClient {
    public static void main(String[] args) {
        MQClient mqClient = new MQClient(new Location(1.0,2.0,3.0),"demo","localhost",8080,new InBoundMessageHandler());
        mqClient.init();
        mqClient.bind();
        mqClient.sendMessage("give me five");
//        mqClient.unbind();
//        mqClient.bind();
    }
}
