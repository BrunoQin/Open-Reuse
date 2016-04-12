package com.openreuse.common.monitor;

import com.openreuse.common.monitor.hook.HookComparator;
import com.openreuse.common.monitor.hook.StatusHook;

import java.util.List;
import java.util.PriorityQueue;

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

    private PriorityQueue<StatusHook> queue
            = new PriorityQueue<StatusHook>(new HookComparator());

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private Status status;

    public void invokeHook(){
        PriorityQueue<StatusHook> newQueue = new PriorityQueue<>(new HookComparator());
        while(queue.size() != 0){
            StatusHook hook = queue.poll();
            hook.onInvoked();
            newQueue.offer(hook);
        }
        this.queue = newQueue;
    }

}
