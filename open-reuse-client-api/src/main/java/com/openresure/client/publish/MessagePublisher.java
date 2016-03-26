package com.openresure.client.publish;

import com.openresure.client.util.MsgHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 16/3/26.
 */
public class MessagePublisher {

    private static List<MsgHandler> msgHandlers = new ArrayList();

    public static void register(MsgHandler msgHandler){
        msgHandlers.add(msgHandler);
    }

    public static void publish(String str){
        for(MsgHandler msgHandler : msgHandlers){
            msgHandler.onMsgReceived(str);
        }
    }

}
