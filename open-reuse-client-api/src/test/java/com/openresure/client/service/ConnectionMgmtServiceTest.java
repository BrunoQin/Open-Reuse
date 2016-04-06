package com.openresure.client.service;

import com.openresure.client.config.ConfigManager;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ryw on 2016/4/6.
 */
public class ConnectionMgmtServiceTest {

    @Test
    public void testDoConnect() throws Exception {
        ConnectionMgmtService.getInstance().doConnect("0.0.0.0");
        assertEquals("0.0.0.0", ConfigManager.getInstance().getCurrentServerAddr());
    }
}