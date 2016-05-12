package com.openreuse.server.response;


import com.openreuse.common.message.Message;
import com.openreuse.server.queue.DiskBackedInMemoryBlockingQueue;
import com.openreuse.server.queue.MappedFileQueue;
import com.openreuse.server.queue.PersistentQueue;

import java.io.IOError;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kimmin on 5/11/16.
 */
public class ResendMsgService {
    private ResendMsgService() {
        try {
            pq = new DiskBackedInMemoryBlockingQueue<>(
                    1024,
                    new MappedFileQueue(
                            "/tmp",
                            "Unreachable Msg Queue",
                            32 * 1024 // 32Mb per page
                    )
            );
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        resendThread = new ResendWorker();
        resendThread.start();
    }
    private static class Singleton {
        private static ResendMsgService instance = new ResendMsgService();
    }
    public static ResendMsgService getInstance(){
        return Singleton.instance;
    }

    private PersistentQueue<Message> pq = null;

    private Thread resendThread = null;

    private volatile boolean runFlag = true;

    public void addFailedMsg(Message msg){
        pq.produce(msg);
    }

    public Message getReadyMsg(){
        return pq.consume();
    }

    private class ResendWorker extends Thread {
        public void run(){
            while(runFlag){
                Message msg = getReadyMsg();
                if(msg == null){
                    try{
                        Thread.currentThread().sleep(10);
                    }catch (InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
                if(getReadyMsg().getTo().equals("MULTICAST")){
                    ResponseService.getInstance().sendMessage(getReadyMsg());
                }else{
                    String to = getReadyMsg().getTo();
                    ResponseService.getInstance().sendMessage(getReadyMsg(), to);
                }
            }
        }
    }

    public void shutdown(){
        runFlag = false;
        try{
            resendThread.join();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        pq.shutdown();
    }

}

