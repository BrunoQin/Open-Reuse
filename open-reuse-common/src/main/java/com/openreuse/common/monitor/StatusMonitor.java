package com.openreuse.common.monitor;

/**
 * Created by kimmin on 4/12/16.
 */
public class StatusMonitor {
    private StatusMonitor(){}
    private static class Singleton {
        private static StatusMonitor instance = new StatusMonitor();
    }
    public static StatusMonitor getInstance(){
        return Singleton.instance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Status status;


}