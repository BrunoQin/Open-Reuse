package com.openreuse.common.logging.queue;

import com.openreuse.common.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by kimmin on 4/22/16.
 */
public class MessageQueueImpl implements IMessageQueue {

    public MessageQueueImpl(int capacity){
        queue = new LinkedBlockingDeque<>(capacity);
    }

    private BlockingQueue<Message> queue = null;

    public Message getMessage(){
        try{
            return queue.take();
        }catch (InterruptedException ie){
            ie.printStackTrace();
            return null;
        }
    }

    public void pushMessage(Message message){
        queue.offer(message);
    }



}
