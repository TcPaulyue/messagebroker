package com.servicematrix.matrixmq.broker.protocol;

public class ProtocolProcess {
    private Connect connect;

    private DisConnect disConnect;

    private Publish publish;

    private Subscribe subscribe;

    public Connect connect(){
        if(connect == null){
            connect = new Connect();
        }
        return connect;
    }

    public DisConnect disConnect(){
        if(disConnect == null){
            disConnect = new DisConnect();
        }
        return disConnect;
    }

    public Publish publish(){
        if(publish == null){
            publish = new Publish();
        }
        return publish;
    }

    public Subscribe subscribe(){
        if(subscribe == null){
            subscribe = new Subscribe();
        }
        return subscribe;
    }
}
