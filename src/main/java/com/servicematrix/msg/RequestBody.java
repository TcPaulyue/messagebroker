package com.servicematrix.msg;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class RequestBody {
    private byte[] body;

    public RequestBody(byte[] body) {
        this.body = body;
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
                "body=" + Arrays.toString(body) +
                '}';
    }

}
