package com.openreuse.server.request.combine;

import com.openreuse.server.misc.worker.WorkerPool;
import com.openreuse.server.request.json.RawBytesWorkerPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Bruno on 16/5/3.
 */
public class CombineWorkerPool implements WorkerPool {

    private CombineWork[] workers;
    private Thread[] workerThreads;

    private static Logger logger = LoggerFactory.getLogger(RawBytesWorkerPool.class);

    public CombineWorkerPool(int number){
        this.workers = new CombineWork[number];
        workerThreads = new Thread[number];
        for(int i=0; i<number; i++){
            workers[i] = new CombineWork(new Date());
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
