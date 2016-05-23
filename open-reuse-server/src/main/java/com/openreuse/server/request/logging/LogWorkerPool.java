package com.openreuse.server.request.logging;

import com.openreuse.server.misc.worker.WorkerPool;
import com.openreuse.server.request.json.RawBytesWorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Bruno on 16/5/23.
 */
public class LogWorkerPool implements WorkerPool {

    private LogWork[] workers;
    private Thread[] workerThreads;

    private static Logger logger = LoggerFactory.getLogger(RawBytesWorkerPool.class);

    public LogWorkerPool(int number){
        this.workers = new LogWork[number];
        workerThreads = new Thread[number];
        for(int i=0; i<number; i++){
            workers[i] = new LogWork(new Date());
        }
    }

    public void startWorkers(){
        for(int i=0; i<workerThreads.length; i++){
            workerThreads[i] = new Thread(workers[i]);
            workerThreads[i].start();
            logger.info("START WORKER SUCCESS!");
        }
    }

    public void stopWorkers(){

        /** Do something **/

    }

}
