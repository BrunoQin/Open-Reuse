package com.openresure.client.service;

import com.openresure.client.config.ConfigManager;
import com.openresure.client.queue.MessageQueue;
import com.openresure.client.sender.MessageSender;
import com.openreuse.common.message.Message;

/**
 * Created by min.jin on 2016/3/28.
 */

public class MessageSendingService {
    private MessageSendingService(){
        this.queue = new MessageQueue();
        this.sender = new MessageSender(
                ConfigManager.getInstance().getChannel(),
                ConfigManager.getInstance().getChannelLock()
        );
        /** Currently simply start it **/
        new Thread(sender).start();
    }
    private static class Singleton{
        private static MessageSendingService instance = new MessageSendingService();
    }
    public static MessageSendingService getInstance(){
        return Singleton.instance;
    }

    private MessageQueue queue;
    private MessageSender sender;

    public void provideMessage(Message message){
        queue.putMessage(message);
    }

    public Message consumeMessage(){
        return queue.getMessage();
    }

}
