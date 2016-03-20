package com.openreuse.server.pojo.notify;

import com.openreuse.server.misc.Constants;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by kimmin on 3/20/16.
 */
public class DelayedNotify implements Delayed {

    public DelayedNotify(){}
    public DelayedNotify(long clientId, long milliDelay){
        this.clientId = clientId;
        this.milliDelay = milliDelay;
    }

    private long clientId = Constants.INVALID_CLIENT_ID; //Default invalid client ID
    private long milliDelay = Constants.INVALID_DELAY_VALUE; //Default invalid delay value

    /** Methods of Delayed Interface **/
    public int compareTo(Delayed delayed){
        if(this.getDelay(TimeUnit.MILLISECONDS) < delayed.getDelay(TimeUnit.MILLISECONDS)){
            return -1;
        }else if(this.getDelay(TimeUnit.MILLISECONDS) > delayed.getDelay(TimeUnit.MILLISECONDS)){
            return 1;
        }else return 0;
    }

    public long getDelay(TimeUnit unit){
        return unit.convert(this.getMilliDelay(), TimeUnit.MILLISECONDS);
    }


    public long getMilliDelay() {
        return milliDelay;
    }

    public void setMilliDelay(long milliDelay) {
        this.milliDelay = milliDelay;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

}
