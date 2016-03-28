package com.openreuse.server.misc.worker;

/**
 * Created by kimmin on 3/25/16.
 */

public interface WorkerPool<T extends Worker> {
    void startWorkers();
    void stopWorkers();
//    void addWorker(T t);
//    void removeWorker(T t);
}
