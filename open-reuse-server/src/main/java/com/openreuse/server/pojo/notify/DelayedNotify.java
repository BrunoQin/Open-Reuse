package com.openreuse.server.pojo.notify;

import com.openreuse.server.misc.Constants;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kimmin on 3/20/16.
 */
public class DelayedNotify implements Delayed {

    public DelayedNotify(long clientId, long nanoDelay){
        this.clientId = clientId;
        this.nanoDelay = nanoDelay;
        this.sequenceNumber = sequencer.getAndIncrement();
        this.executeTime = now() + nanoDelay;
    }

    private long clientId; //Default invalid client ID


    private long nanoDelay; //Default invalid delay value
    private long executeTime;
    public final long sequenceNumber;

    private static final long NANO_ORIGIN = System.nanoTime();
    private static final AtomicLong sequencer = new AtomicLong(0);


    final static long now() {
        return System.nanoTime() - NANO_ORIGIN;
    }

    /** Methods of Delayed Interface **/
    @Override
    public int compareTo(Delayed delayed){
        if(! (delayed instanceof DelayedNotify)
                || this == null) return 0;
        if(delayed == this) return 0;
        DelayedNotify delayedNotify = (DelayedNotify) delayed;
        if(this.getDelay(TimeUnit.NANOSECONDS) < delayedNotify.getDelay(TimeUnit.NANOSECONDS)){
            return -1;
        }else if(this.getDelay(TimeUnit.NANOSECONDS) > delayedNotify.getDelay(TimeUnit.NANOSECONDS)){
            return 1;
        }else if(this.sequenceNumber < delayedNotify.sequenceNumber){
            return -1;
        }else return 0;
    }

    public long getDelay(TimeUnit unit){
        return unit.convert(this.executeTime - now(), TimeUnit.NANOSECONDS);
    }


    public long getNanoDelay() {
        return nanoDelay;
    }

    public long getClientId() {
        return clientId;
    }

}
