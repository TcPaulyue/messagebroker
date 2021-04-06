package com.servicematrix.matrixmq.broker.strategy;

import com.servicematrix.matrixmq.msg.client.RequestMessage;

import java.util.List;

public abstract class BrokerStrategy<T,S> {
    private T t;

    public BrokerStrategy(T t) {
        this.t = t;
    }

    abstract List<S> routing(RequestMessage requestMessage);


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
