package com.openreuse.client.api;

import com.openresure.client.ClientAgent;
import org.junit.Test;

/**
 * Created by min.jin on 2016/3/28.
 */
public class TestAPI {

    @Test
    public void testValidate(){
        assert ClientAgent.loginValidate("127.0.0.1", "jinmin", "123456");
    }

    @Test
    public void testRegister(){
//        assert ClientAgent.
    }
}
