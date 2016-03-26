package com.openreuse.server.throttle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kimmin on 3/26/16.
 */

public class ThrottleStatsManager {
    private ThrottleStatsManager(){}
    private static class Singleton {
        private static ThrottleStatsManager instance = new ThrottleStatsManager();
    }
    public static ThrottleStatsManager getInstance(){
        return Singleton.instance;
    }
    /** Map for counting message count after usr login **/
    private Map<Long, Long> messageCntMap = new ConcurrentHashMap<Long, Long>();
    /** Statistic for count Blah blah.. **/
    private AtomicLong validLoginCount = new AtomicLong(0);
    private AtomicLong invalidLoginCount = new AtomicLong(0);
    private AtomicLong forwardedMsgCount = new AtomicLong(0);


}
