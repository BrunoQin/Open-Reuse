package com.openreuse.server.throttle;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.openreuse.server.misc.Constants;
import com.openreuse.server.pojo.notify.DelayedNotify;
import com.openreuse.server.registry.RegistryManager;
import com.openreuse.server.validate.Validators;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kimmin on 3/20/16.
 */
public class ThrottleManager {
    private ThrottleManager(){}
    private static class Singleton {
        private static ThrottleManager instance = new ThrottleManager();
    }
    public static ThrottleManager getInstance(){
        return Singleton.instance;
    }

    /** Scheduled pool for managing incoming log-in frequency **/
    private DelayQueue<DelayedNotify> delayQueue = new DelayQueue<DelayedNotify>();
    private Thread[] workerThreads = new Thread[Constants.THROTTLE_WORK_NUMBER];
    private AtomicBoolean runFlag = new AtomicBoolean(true);

    public void notity(DelayedNotify notify){
        assert Validators.validateNotify(notify);
        delayQueue.offer(notify, notify.getMilliDelay(), TimeUnit.MILLISECONDS);
    }


    private class ThrottleWorker implements Runnable {
        public ThrottleWorker(DelayQueue<DelayedNotify> queue, AtomicBoolean runFlag){
            this.queue = queue;
            this.runFlag = runFlag;
        }
        private DelayQueue<DelayedNotify> queue;
        private AtomicBoolean runFlag;

        public void run(){
            while(runFlag.get()){
                try{
                    DelayedNotify expiredNotify = queue.poll(1000,TimeUnit.MILLISECONDS);
                    long clientID = expiredNotify.getClientId();
                    RegistryManager.getInstance().countDownLogin(clientID);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void startWorker(){
        for(Thread workerThread: workerThreads){
            workerThread = new Thread(
                    new ThrottleWorker(delayQueue, runFlag));
            workerThread.start();
        }
    }

    public void stopWorker(){
        runFlag.compareAndSet(true, false);
        for(Thread workerThread: workerThreads){
            try{
                workerThread.join();
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }

}
