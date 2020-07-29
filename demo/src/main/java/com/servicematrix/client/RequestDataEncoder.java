package com.servicematrix.client;

import com.servicematrix.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

public class RequestDataEncoder
        extends MessageToByteEncoder<Message> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          Message msg, ByteBuf out) throws Exception {

        out.writeInt(msg.getNum());
        out.writeInt(msg.getId().length());
        out.writeCharSequence(msg.getId(), charset);
    }
}