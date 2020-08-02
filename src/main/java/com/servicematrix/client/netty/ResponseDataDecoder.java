package com.servicematrix.client.netty;

import com.servicematrix.serialize.MessageCodecUtil;
import com.servicematrix.server.RequestDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class ResponseDataDecoder extends ByteToMessageDecoder {

    final public static int MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;

    //   private final Charset charset = Charset.forName("UTF-8");

    private MessageCodecUtil util;

    public ResponseDataDecoder(MessageCodecUtil messageCodecUtil){
        this.util = messageCodecUtil;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {

        if (in.readableBytes() < RequestDecoder.MESSAGE_LENGTH) {
            return;
        }

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