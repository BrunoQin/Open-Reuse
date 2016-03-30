package com.openreuse.window;

import com.openresure.client.service.MessageNotifyService;
import com.openreuse.common.message.MessageType;
import com.openreuse.window.body.LoginWindow;
import com.openreuse.window.listener.PrintToWindowListener;

/**
 * Created by kimmin on 3/21/16.
 */
public class WindowApp {

//    static{
//        MessageNotifyService.getInstance().registerListener(MessageType.TEXT_MESSAGE, new PrintToWindowListener());
//    }

    public static void main(String[] args){
        /** Make your instance of windows body here **/
        new LoginWindow();
    }

}
