package com.servicematrix.server;

import com.servicematrix.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class RequestDecoder extends ReplayingDecoder<Message> {

    private final Charset charset = Charset.forName("UTF-8");

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {

        Message data = new Message();
        data.setNum(in.readInt());
        int strLen = in.readInt();
        data.setTopic(
                in.readCharSequence(strLen, charset).toString());
        out.add(data);
    }
}