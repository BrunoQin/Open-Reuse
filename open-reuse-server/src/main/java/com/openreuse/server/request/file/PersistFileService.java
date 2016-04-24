package com.openreuse.server.request.file;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.request.json.ParseJsonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Bruno on 16/4/24.
 */
public class PersistFileService {

    private static Logger logger = LoggerFactory.getLogger(ParseJsonService.class);

    private AtomicLong atomicLong = new AtomicLong(0);

    private PersistFileWorkerPool workerPool;

    private PersistFileService() {
        this.workerPool = new PersistFileWorkerPool(Constants.FILE_WORKER_NUMBER);
        this.workerPool.startWorkers();
    }

    private static class Singleton {
        private static PersistFileService instance = new PersistFileService();
    }

    public static PersistFileService getInstance(){
        return Singleton.instance;
    }

}
