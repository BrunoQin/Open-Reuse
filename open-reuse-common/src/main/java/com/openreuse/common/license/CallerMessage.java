package com.openreuse.common.license;

/**
 * Created by kimmin on 4/12/16.
 */
public class CallerMessage {
    public CallerMessage(byte[] raw){
        this.bytes = raw;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    private byte[] bytes;
}
