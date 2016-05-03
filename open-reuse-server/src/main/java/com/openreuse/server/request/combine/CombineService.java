package com.openreuse.server.request.combine;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.request.json.ParseJsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Bruno on 16/5/3.
 */
public class CombineService {

    private static Logger logger = LoggerFactory.getLogger(ParseJsonService.class);

    private AtomicLong atomicLong = new AtomicLong(0);

    private CombineWorkerPool workerPool;

    private CombineService() {
        this.workerPool = new CombineWorkerPool(Constants.COMBINE_WORKER_NUMBER);
        this.workerPool.startWorkers();
    }

    private static class Singleton {
        private static CombineService instance = new CombineService();
    }

    public static CombineService getInstance(){
        return Singleton.instance;
    }

}
