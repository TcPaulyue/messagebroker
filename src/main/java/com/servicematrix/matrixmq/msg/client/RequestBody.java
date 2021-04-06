package com.servicematrix.matrixmq.msg.client;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class RequestBody {
    private byte[] body;

    public RequestBody(byte[] body) {
        this.body = body;
    }

    public RequestBody() {
    }

    public String getBody() throws UnsupportedEncodingException {
        return new String(body, "UTF-8");
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "body=" + new String(body) +
                '}';
    }

}
