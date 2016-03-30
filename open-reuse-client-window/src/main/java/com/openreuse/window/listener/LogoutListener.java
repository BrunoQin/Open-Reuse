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
        if(null == this.window) return;
        /** Message body contains username only **/
        window.somebodyLogout(body);
    }

    public boolean isValid(){
        return null != this.window;
    }
}
