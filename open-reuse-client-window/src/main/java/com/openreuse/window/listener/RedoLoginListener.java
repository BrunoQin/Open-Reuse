package com.openreuse.window.listener;

import com.openresure.client.listener.MessageListener;
import com.openreuse.window.body.LoginWindow;
import com.openreuse.window.body.SendMessageWindow;

/**
 * Created by kimmin on 3/28/16.
 */
public class RedoLoginListener implements MessageListener {

    public RedoLoginListener(SendMessageWindow window){
        this.window = window;
    }

    private SendMessageWindow window;

    public void onMessageArrive(String body){
        window.frame.dispose();
        new LoginWindow();
    }

}
