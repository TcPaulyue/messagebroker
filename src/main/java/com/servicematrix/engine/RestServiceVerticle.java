package com.servicematrix.engine;


import com.servicematrix.client.ClientMessageSender;
import com.servicematrix.msg.RequestAccessibleMessage;
import com.servicematrix.msg.RequestBody;
import com.servicematrix.msg.RequestHeader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;


import java.util.HashMap;
import java.util.Map;

import static com.servicematrix.engine.ChannelInfoMapReceiver.clientsLocationMap;


public class RestServiceVerticle extends AbstractVerticle {

    private ClientMessageSender clientMessageSender;

    private static Map<String, Map<String, Boolean>> accessibleMap = new HashMap<>();

    public RestServiceVerticle(ClientMessageSender clientMessageSender) {
        this.clientMessageSender = clientMessageSender;
        this.bindToNetty();
        this.sendLocationMapToNetty();
    }

    private void bindToNetty() {
        RequestHeader requestHeader = new RequestHeader();
        String message = "AccessibleJudgmentEngine join in...";
        RequestBody requestBody = new RequestBody(message.getBytes());
        clientMessageSender.mapEngine(requestHeader, requestBody);
    }

    private void sendLocationMapToNetty(){
        Map<String,Boolean> stringBooleanMap = new HashMap<>();
        stringBooleanMap.put("person",true);
        stringBooleanMap.put("person01",false);
        accessibleMap.put("coffeeMachine",stringBooleanMap);
        RequestAccessibleMessage requestAccessibleMessage = new RequestAccessibleMessage(accessibleMap);
        clientMessageSender.sendAccessibleMap(requestAccessibleMessage);
    }

    @Override
    public void start(Future<Void> future) {

        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(io.vertx.core.http.HttpMethod.GET)
                .allowedMethod(io.vertx.core.http.HttpMethod.POST)
                .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
                .allowedHeader("Access-Control-Request-Method")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Headers")
                .allowedHeader("Content-Type"));

        router.post("/api/channelInfos")
                .handler(this::getChannelInfos);
        HttpServer server = vertx.createHttpServer();

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8081), result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }
                });
    }


    private void getChannelInfos(RoutingContext routingContext) {

        routingContext.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(200)
                .end(Json.encodePrettily(clientsLocationMap));
    }

    public static void main(String[] args) throws Exception {
        //最后一个参数是用来处理接收到的消息
        ClientMessageSender clientMessageSender = new ClientMessageSender("localhost", 8082, new ChannelInfoMapReceiver());
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RestServiceVerticle(clientMessageSender));

    }
}