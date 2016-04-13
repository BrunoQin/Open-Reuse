package com.openreuse.common.license;

/**
 * Created by kimmin on 4/13/16.
 */
public class License {
    public License(){}
    public License(byte[] bytes){
        this.key = bytes;
    }

    private byte[] key;

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

}
