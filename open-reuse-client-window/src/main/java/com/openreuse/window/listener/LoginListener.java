package com.openreuse.window.listener;

import com.openresure.client.listener.MessageListener;
import com.openreuse.window.body.SendMessageWindow;

import javax.swing.*;

/**
 * Created by kimmin on 3/28/16.
 */
public class LoginListener implements MessageListener{

    public LoginListener(SendMessageWindow sendMessageWindow){
        this.window = sendMessageWindow;
    }

    private SendMessageWindow window;

    public void onMessageArrive(String body){
        /** Message body contains username only **/
        window.somebodyLogin(body);
    }

    public boolean isValid(){
        return null != this.window;
    }
}
