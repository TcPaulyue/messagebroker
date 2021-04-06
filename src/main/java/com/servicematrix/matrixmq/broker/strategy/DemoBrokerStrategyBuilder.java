package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

import java.util.ArrayList;
import java.util.List;

public class DemoBrokerStrategyBuilder extends  BrokerStrategyBuilder {

    @Override
    public List<String> getBinderList(RequestMessage requestMessage,List<BrokerStrategy> brokerStrategyList) {
        List<String> results = new ArrayList<>();
        brokerStrategyList.forEach(brokerStrategy -> {
           brokerStrategy.routing(requestMessage).forEach(key->{
               if(!results.contains(key))
                   results.add((String)key);
           });
        });
        return results;
    }
}
