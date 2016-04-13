package com.openreuse.common.license;

/**
 * Created by kimmin on 4/13/16.
 */
public class License {
    public License(byte[] bytes){
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    private byte[] bytes;

}
