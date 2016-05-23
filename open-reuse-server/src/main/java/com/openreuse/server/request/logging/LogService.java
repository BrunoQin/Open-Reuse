package com.openreuse.server.request.logging;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.request.json.ParseJsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Bruno on 16/5/23.
 */
public class LogService {

    private static Logger logger = LoggerFactory.getLogger(ParseJsonService.class);

    private AtomicLong atomicLong = new AtomicLong(0);

    private LogWorkerPool workerPool;

    private LogService() {
        this.workerPool = new LogWorkerPool(Constants.FILE_WORKER_NUMBER);
        this.workerPool.startWorkers();
    }

    private static class Singleton {
        private static LogService instance = new LogService();
    }

    public static LogService getInstance(){
        return Singleton.instance;
    }


}
