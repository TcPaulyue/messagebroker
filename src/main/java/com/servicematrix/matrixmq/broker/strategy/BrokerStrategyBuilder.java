package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

import java.util.List;

public abstract class BrokerStrategyBuilder {

    public abstract List<String> getBinderList(RequestMessage requestMessage,List<BrokerStrategy> brokerStrategyList);

}
