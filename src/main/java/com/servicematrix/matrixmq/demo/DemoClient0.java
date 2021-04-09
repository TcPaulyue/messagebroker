package com.servicematrix.matrixmq.demo;

import com.servicematrix.matrixmq.client.AppContext;
import com.servicematrix.matrixmq.client.MQClient;
import com.servicematrix.matrixmq.msg.client.Location;

public class DemoClient0 {
    public static void main(String[] args) {
        MQClient mqClient = new MQClient(new Location(1.0,2.0,3.0),"demo0","localhost",8080,new InBoundMessageHandler());
        mqClient.init();
        mqClient.bind();
        AppContext appContext = new AppContext("appCtx0",mqClient);
        appContext.init();
//        while(true){
//            appContext.sendMessage("turn on my air purifier.");
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        //mqClient.unbind();

    }
}
