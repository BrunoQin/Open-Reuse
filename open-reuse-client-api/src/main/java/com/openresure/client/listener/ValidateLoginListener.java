package com.openresure.client.listener;

import com.openresure.client.config.ConfigManager;
import com.openreuse.server.misc.Constants;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ValidateLoginListener implements MessageListener {
    public void onMessageArrive(String body){
        /** The body is who actually logined **/
        ConfigManager.getInstance().setLogined(body);
    }
}
