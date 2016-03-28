package com.openreuse.window.listener;

import com.openresure.client.listener.MessageListener;
import com.openreuse.window.body.SendMessageWindow;

/**
 * Created by kimmin on 3/28/16.
 */
public class LogoutListener implements MessageListener {

    public LogoutListener(SendMessageWindow window){
        this.window = window;
    }

    private SendMessageWindow window;

    public void onMessageArrive(String body){
        /** Message body contains username only **/
        window.somebodyLogout(body);
    }
}
