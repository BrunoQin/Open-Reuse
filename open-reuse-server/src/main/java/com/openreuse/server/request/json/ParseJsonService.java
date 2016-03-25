package com.openreuse.server.request.json;

import com.openreuse.server.misc.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kimmin on 3/25/16.
 */
public class ParseJsonService {
    /** Singleton **/
    private ParseJsonService() {
        this.workerPool = new RawBytesWorkerPool(queue, Constants.PARSE_WORKER_NUMBER);
        this.queue = new LinkedBlockingQueue<byte[]>(Constants.MAX_JSON_SIZE);
        this.workerPool.startWorkers();
    }
    private static class Singleton {
        private static ParseJsonService instance = new ParseJsonService();
    }
    public static ParseJsonService getInstance(){
        return Singleton.instance;
    }

    private static Logger logger = LoggerFactory.getLogger(ParseJsonService.class);

    private AtomicLong atomicLong = new AtomicLong(0);

    private BlockingQueue<byte[]> queue;

    private RawBytesWorkerPool workerPool;

    public void provideRawBytes(byte[] rawBytes) {
        try {
            boolean success = queue.offer(rawBytes, 1000, TimeUnit.MILLISECONDS);
            if(!success){
                atomicLong.getAndIncrement();
                logger.warn("CANNOT OFFER RAW JSON BYTES:", new String(rawBytes));
                logger.warn("{} JSONS HAVE BEEN MISSED YET!", atomicLong.get());
            }
        }catch (InterruptedException ie){
            logger.warn("OFFER RAW JSON INTERRUPTED : {}", new String(rawBytes));
            ie.printStackTrace();
        }
    }

}
