package com.openresure.client;

import com.openresure.client.config.ConfigManager;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ryw on 2016/4/6.
 */
public class ClientAgentTest {

    @Test

    public void testRegisterValidate() throws Exception {
        ClientAgent.registerValidate("127.0.0.1","Wen","1234");
        assertEquals("127.0.0.1",ConfigManager.getInstance().getCurrentServerAddr());
        assertEquals(true, ConfigManager.getInstance().registerSuccess);
    }

    @Test
    public void testLoginValidate() throws Exception {
        ClientAgent.loginValidate("127.0.0.1", "Wen", "1234");
        assertEquals(false,ConfigManager.getInstance().isLogined("Wen"));
        assertEquals("127.0.0.1",ConfigManager.getInstance().getCurrentServerAddr());
    }

    @Test
    public void testLogout() throws Exception {
        ClientAgent.logout("Wen");
        assertEquals(false, ConfigManager.getInstance().isLogined("Wen"));
    }
    @Test
    public void testRegisterListener() throws Exception {

    }

    @Test
    public void testConfigureServer() throws Exception {

    }


    @Test
    public void testSendTextMessage() throws Exception {

    }
}