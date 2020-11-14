package com.servicematrix.matrixmq.netty;

import com.servicematrix.matrixmq.serialize.MessageCodecUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class RequestDataDecoder extends ByteToMessageDecoder {

    final public static int MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;

    //   private final Charset charset = Charset.forName("UTF-8");

    private MessageCodecUtil util;

    public RequestDataDecoder(MessageCodecUtil messageCodecUtil) {
        this.util = messageCodecUtil;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < RequestDataDecoder.MESSAGE_LENGTH) { return; }
        in.markReaderIndex();
        int messageLength = in.readInt();
        if (messageLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);
            try {
                Object obj = util.decode(messageBody);
                out.add(obj);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}