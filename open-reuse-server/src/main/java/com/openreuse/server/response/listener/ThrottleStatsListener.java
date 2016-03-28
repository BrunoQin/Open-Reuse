package com.openreuse.server.response.listener;

import com.openreuse.server.throttle.ThrottleStatsManager;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ThrottleStatsListener implements Runnable {
    public void run(){
        ThrottleStatsManager.getInstance().incForwardedMsgCount();
    }
}
