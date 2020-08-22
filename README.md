##使用说明

在`/example`目录下实现了一个简单的`message broker`和一个注册到这个messagebroker上面的`client`，可以参考其中的实现。

###消息格式

####客户端消息
在发送消息时需要指定`RequestHeader`，它里面目前有三个参数`time`,`location`和`destination`，`time`想表示客户端的时间属性，`location`用来表示空间属性，`destination`表示消息的接收者。
具体的消息内容以`byte[]`的形式存入`RequestBody`中。


###接口
发送请求：
+ bind() :向mq发送绑定请求
+ sendMessage(): 发送消息
+ unbind() : 和mq解除绑定

接收请求：
 (your class) extends ServerMessageHandler(){...}
