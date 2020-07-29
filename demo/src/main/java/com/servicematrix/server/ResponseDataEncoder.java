package com.servicematrix.server;

import com.servicematrix.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder
        extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          Message msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getNum());
    }
}
