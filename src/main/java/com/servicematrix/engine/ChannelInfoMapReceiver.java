package com.servicematrix.engine;

import com.alibaba.fastjson.JSONObject;
import com.servicematrix.client.netty.ServerMessageHandler;
import com.servicematrix.msg.Location;
import com.servicematrix.msg.RequestMessage;


import java.util.HashMap;
import java.util.Map;

public class ChannelInfoMapReceiver extends ServerMessageHandler {

    public static Map<String, Location> clientsLocationMap = new HashMap<>();

    @Override
    public void responseMessage(RequestMessage requestMessage) throws InterruptedException {
        System.out.println(requestMessage.toString());
    }

    @Override
    public void ackMessage(String msg) {
        JSONObject locationMap = JSONObject.parseObject(msg);
        locationMap.forEach((key,value)->{
            JSONObject a = JSONObject.parseObject(value.toString());
            Location location = new Location(a.getDouble("x"),a.getDouble("y"),a.getDouble("z"));
            clientsLocationMap.put(key,location);
        });
    }
}