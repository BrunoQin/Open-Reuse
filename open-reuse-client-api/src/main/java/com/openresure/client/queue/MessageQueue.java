package com.openresure.client.queue;

import com.openreuse.common.message.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by min.jin on 2016/3/28.
 */
public class MessageQueue {

    private BlockingQueue<Message> queue = new LinkedBlockingDeque<Message>(1024);

    /** Uhh? **/
    private BlockingQueue<Message> getQueue(){
        return queue;
    }

    public Message getMessage(){
        try{
            Message message = queue.poll(1000, TimeUnit.MILLISECONDS);
            return message;
        }catch (InterruptedException ie){
            ie.printStackTrace();
            return null;
        }
    }

    public void putMessage(Message message){
        try{
            /** Only wait 1 second **/
            queue.offer(message, 1000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

}
