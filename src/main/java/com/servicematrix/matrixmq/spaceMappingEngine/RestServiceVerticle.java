//package com.servicematrix.matrixmq.spaceMappingEngine;
//
//
//import com.servicematrix.client.ClientMessageSender;
//import com.servicematrix.msg.RequestAccessibleMessage;
//import com.servicematrix.msg.RequestBody;
//import com.servicematrix.msg.RequestHeader;
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Future;
//import io.vertx.core.Vertx;
//import io.vertx.core.http.HttpServer;
//import io.vertx.core.json.Json;
//import io.vertx.ext.web.Router;
//import io.vertx.ext.web.RoutingContext;
//import io.vertx.ext.web.handler.CorsHandler;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.servicematrix.spaceMappingEngine.ChannelInfoMapReceiver.clientsLocationMap;
//
//
//public class RestServiceVerticle extends AbstractVerticle {
//
//    private ClientMessageSender clientMessageSender;
//
//    public static Map<String, Map<String, Double>> accessibleMap = new HashMap<>();
//
//    private static List<String> elements = Arrays.asList("coffeeMachine01","coffeeMachine02","paul");
//
//    private RestServiceVerticle(ClientMessageSender clientMessageSender) {
//        this.clientMessageSender = clientMessageSender;
//        this.bindToNetty();
//        this.initAccessibleMap();
//        this.sendLocationMapToNetty();
//    }
//
//    public void initAccessibleMap(){
//        for(String s:elements){
//            Map<String,Double> stringBooleanMap = new HashMap<>();
//            for(String s1:elements){
//                stringBooleanMap.put(s1,0.0);
//            }
//            accessibleMap.put(s,stringBooleanMap);
//        }
//    }
//
//    private void bindToNetty() {
//        RequestHeader requestHeader = new RequestHeader();
//        String message = "AccessibleJudgmentEngine join in...";
//        RequestBody requestBody = new RequestBody(message.getBytes());
//        clientMessageSender.mapEngine(requestHeader, requestBody);
//    }
//
//    private void sendLocationMapToNetty(){
//        accessibleMap.get("coffeeMachine01").put("paul",16.0);
//        accessibleMap.get("paul").put("coffeeMachine01",16.0);
//        RequestAccessibleMessage requestAccessibleMessage = new RequestAccessibleMessage(accessibleMap);
//        clientMessageSender.sendAccessibleMap(requestAccessibleMessage);
//    }
//
//    @Override
//    public void start(Future<Void> future) {
//
//        Router router = Router.router(vertx);
//        router.route().handler(CorsHandler.create("*")
//                .allowedMethod(io.vertx.core.http.HttpMethod.GET)
//                .allowedMethod(io.vertx.core.http.HttpMethod.POST)
//                .allowedMethod(io.vertx.core.http.HttpMethod.OPTIONS)
//                .allowedHeader("Access-Control-Request-Method")
//                .allowedHeader("Access-Control-Allow-Credentials")
//                .allowedHeader("Access-Control-Allow-Origin")
//                .allowedHeader("Access-Control-Allow-Headers")
//                .allowedHeader("Content-Type"));
//
//        router.post("/api/channelInfos")
//                .handler(this::getChannelInfos);
//        HttpServer server = vertx.createHttpServer();
//
//        vertx.createHttpServer()
//                .requestHandler(router::accept)
//                .listen(config().getInteger("http.port", 8081), result -> {
//                    if (result.succeeded()) {
//                        future.complete();
//                    } else {
//                        future.fail(result.cause());
//                    }
//                });
//    }
//
//
//    private void getChannelInfos(RoutingContext routingContext) {
//
//        routingContext.response()
//                .putHeader("content-type", "application/json")
//                .setStatusCode(200)
//                .end(Json.encodePrettily(clientsLocationMap));
//    }
//
//    public static void main(String[] args) throws Exception {
//        //最后一个参数是用来处理接收到的消息
//        ClientMessageSender clientMessageSender = new ClientMessageSender("localhost", 8082, new ChannelInfoMapReceiver());
//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(new RestServiceVerticle(clientMessageSender));
//
//    }
//}