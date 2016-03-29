package com.openreuse.server.request.json;

import com.openreuse.server.misc.worker.WorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kimmin on 3/25/16.
 */
public class RawBytesWorkerPool implements WorkerPool<RawBytesWorker> {

    private static Logger logger = LoggerFactory.getLogger(RawBytesWorkerPool.class);

    public RawBytesWorkerPool(BlockingQueue queue, int number){
        this.runFlag = new AtomicBoolean(true);
        this.workers = new RawBytesWorker[number];
        workerThreads = new Thread[number];
        for(int i=0; i<number; i++){
            workers[i] = new RawBytesWorker(queue, runFlag);
        }
    }

    private RawBytesWorker[] workers;
    private AtomicBoolean runFlag;
    private Thread[] workerThreads;

    public void startWorkers(){
        for(int i=0; i<workerThreads.length; i++){
            workerThreads[i] = new Thread(workers[i]);
            workerThreads[i].start();
            logger.info("START WORKER SUCCESS!");
        }
    }

    public void stopWorkers(){
        /** Cyclic CAS **/
        while(runFlag.get()){
            runFlag.compareAndSet(true, false);
        }
        for(Thread workerThread: workerThreads){
            try {
                workerThread.join();
            }catch (InterruptedException ie){
                ie.printStackTrace();
                logger.error("STOP WORKER FAILURE!");
                logger.error("THREAD LEAKAGE DANGER!");
            }
        }
    }

//    /** NO-USE METHOD :-) **/
//    public void addWorker(RawBytesWorker worker){
//        workers.add(worker)
//    }
//    public void removeWorker(RawBytesWorker worker){
//        workers.remove(worker);
//    }
}
