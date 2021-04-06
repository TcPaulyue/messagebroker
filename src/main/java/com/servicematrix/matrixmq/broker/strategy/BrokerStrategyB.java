package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

import java.util.List;

public class BrokerStrategyB extends BrokerStrategy<List<String>,String> {

    public BrokerStrategyB(List<String> strings) {
        super(strings);
    }

    @Override
    List<String> routing(RequestMessage requestMessage) {
        return this.getT();
    }
}
