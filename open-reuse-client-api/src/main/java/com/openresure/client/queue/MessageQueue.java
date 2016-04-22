package com.openresure.client.queue;

import com.openreuse.common.log.queue.IMessageQueue;
import com.openreuse.common.log.queue.MessageQueueImpl;
import com.openreuse.common.message.Message;
import com.openreuse.server.misc.Constants;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by min.jin on 2016/3/28.
 */
public class MessageQueue {

    private BlockingQueue<Message> queue = new LinkedBlockingDeque<Message>(1024);

    private static IMessageQueue mq = new MessageQueueImpl(Constants.DUMP_MQ_CAPACITY);

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

    public static void dumpMessageToQueue(Message message){
        mq.pushMessage(message);
    }

    public static Message undumpMessageFromQueue(){
        return mq.getMessage();
    }

}
