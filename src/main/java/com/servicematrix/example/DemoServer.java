package com.servicematrix.example;

import com.servicematrix.server.NettyServer;

public class DemoServer extends NettyServer {
    public DemoServer() {
    }
    public static void main(String[] args) throws Exception {
        DemoServer demoServer = new DemoServer();
        demoServer.start(8082);  //指定messagebroker的启动端口，启动mq。所有client需要绑定该端口
    }
}
