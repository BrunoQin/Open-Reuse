package com.openresure.client.listener;

import com.openresure.client.config.ConfigManager;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ValidateRegisterListener implements MessageListener {

    public void onMessageArrive(String body){
        String username = ConfigManager.getInstance().registername;
        if(username.equals(body)){
            ConfigManager.getInstance().registerSuccess = true;
        }
    }
}
