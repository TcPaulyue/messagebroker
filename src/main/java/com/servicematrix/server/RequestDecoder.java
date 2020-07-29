package com.servicematrix.server;

import com.servicematrix.client.RequestData;
import com.servicematrix.serialize.MessageCodecUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class RequestDecoder extends ByteToMessageDecoder {

    final public static int MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;

 //   private final Charset charset = Charset.forName("UTF-8");

    private MessageCodecUtil util;

    public RequestDecoder(MessageCodecUtil messageCodecUtil){
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

//        RequestData data = new RequestData();
//        data.setCount(in.readInt());
//        int strLen = in.readInt();
//        data.setTopic(
//                in.readCharSequence(strLen, charset).toString());
//        strLen = in.readInt();
//        data.setMessage( in.readCharSequence(strLen, charset).toString());
//        out.add(data);
    }
}