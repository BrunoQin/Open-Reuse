package com.openreuse.window.listener;

import com.openresure.client.listener.MessageListener;
import com.openreuse.window.body.SendMessageWindow;

/**
 * Created by kimmin on 3/28/16.
 */
public class PrintToWindowListener implements MessageListener {

    public PrintToWindowListener(SendMessageWindow window){
        this.window = window;
    }

    private SendMessageWindow window;

    public void onMessageArrive(String body){
        window.sendMessage(body);
    }


    public boolean isValid(){
        return null != this.window;
    }
}
