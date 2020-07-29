package com.servicematrix.client;

import com.servicematrix.serialize.MessageCodecUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class RequestDataEncoder extends MessageToByteEncoder<Object> {

    private MessageCodecUtil util = null;

    RequestDataEncoder(MessageCodecUtil messageCodecUtil){
        this.util = messageCodecUtil;
    }

//    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          Object msg, ByteBuf out) throws Exception {

        util.encode(out,msg);
//        out.writeInt(msg.getCount());
//        out.writeInt(msg.getTopic().length());
//        out.writeCharSequence(msg.getTopic(), charset);
//        out.writeInt(msg.getMessage().length());
//        out.writeCharSequence(msg.getMessage(),charset);
    }
}