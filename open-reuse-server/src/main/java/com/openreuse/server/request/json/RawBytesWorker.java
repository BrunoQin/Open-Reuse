package com.openreuse.server.request.json;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.server.misc.worker.Worker;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kimmin on 3/25/16.
 */
public class RawBytesWorker implements Worker {

    public RawBytesWorker(BlockingQueue<byte[]> queue, AtomicBoolean runFlag){
        this.queue = queue;
        this.runFlag = runFlag;
        logger.info("A RAW JSON BYTES WORKER NEWED!");
    }

    private BlockingQueue<byte[]> queue;
    private ObjectMapper om = new ObjectMapper();
    private AtomicBoolean runFlag;

    private static Logger logger = LoggerFactory.getLogger(RawBytesWorker.class);

    public void work(){
        try{
            byte[] rawBytes = queue.poll(1000, TimeUnit.MILLISECONDS);
            try{
                Message message = om.readValue(rawBytes, Message.class);
                /** Do message verification here **/
                String from = message.getFrom();


                /** Dispatch the message toward different services  **/


            }catch (IOException ioe){
                ioe.printStackTrace();
                logger.warn("CORRUPTED RAW JSON BYTES RECEIVED!");
            }
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }

    public void run(){
        while(runFlag.get()){
            this.work();
        }
    }

}
