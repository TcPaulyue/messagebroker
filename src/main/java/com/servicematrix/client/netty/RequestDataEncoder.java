package com.servicematrix.client.netty;

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
    }
}