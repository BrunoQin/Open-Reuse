package com.openresure.client.listener;

import com.openreuse.common.message.Message;

/**
 * Created by kimmin on 5/12/16.
 */
public class OnlineNotifyListener implements MessageListener {
    public void onMessageArrive(String body){
        /**
         *  Show in the window :-)
         *  The body contains a list of group members' names
         *  Each message represents a unique group
         * **/

    }
    public boolean isValid(){
        return true;
    }
}
