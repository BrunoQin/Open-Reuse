package com.openreuse.server.test.basic;

import org.junit.Test;

/**
 * Created by kimmin on 3/23/16.
 */
public class SimpleClientConnTest {

    @Test
    public void connectionTest(){
        int port = 8080;
        try{
            new SimpleClient().connect(30000, "127.0.0.1");
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

}
