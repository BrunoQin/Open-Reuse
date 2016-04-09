package com.openreuse.server.throttle;

import com.openreuse.common.persist.LocalPersistHelper;
import com.openreuse.server.misc.Constants;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kimmin on 3/26/16.
 */

public class ThrottleStatsManager {
    private ThrottleStatsManager(){
        dumpTimer = new Timer();
        dumpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                LocalPersistHelper.persistMapToLocal(statsMap,
                        new File("stats.json"));
            }
        }, 1, Constants.REDUMP_STATS_INTERVAL_MILLIS);
    }
    private static class Singleton {
        private static ThrottleStatsManager instance = new ThrottleStatsManager();
    }
    public static ThrottleStatsManager getInstance(){
        return Singleton.instance;
    }
    /** Map for counting message count after usr login **/
    private Map<Long, Integer> messageCntMap = new ConcurrentHashMap<Long, Integer>();

    /** Statistic for count Blah blah.. **/
    private AtomicLong validLoginCount = new AtomicLong(0);
    private AtomicLong invalidLoginCount = new AtomicLong(0);
    private AtomicLong forwardedMsgCount = new AtomicLong(0);
    private AtomicLong receivedMsgCount = new AtomicLong(0);
    private AtomicLong ignoredMsgCount = new AtomicLong(0);

    private static Map<String, Long> statsMap = new ConcurrentHashMap<String, Long>();

    public void refreshStatsMap(){
        statsMap.put("validLoginCount", validLoginCount.get());
        statsMap.put("invalidLoginCount", invalidLoginCount.get());
        statsMap.put("forwardedMsgCount", forwardedMsgCount.get());
        statsMap.put("receivedMsgCount", receivedMsgCount.get());
        statsMap.put("ignoredMsgCount", ignoredMsgCount.get());
    }

    private Timer dumpTimer;

    public int getMsgCount(long uid){
        Integer cnt = messageCntMap.get(uid);
        return cnt;
    }
    public void incMsgCount(long uid){
        Integer cnt = messageCntMap.get(uid);
        if(null == cnt){
            cnt = 1;
        }else{
            cnt ++;
        }
        messageCntMap.put(uid, cnt);
    }

    public boolean checkMsgCount(long uid){
        Integer cnt = messageCntMap.get(uid);
        if(null != cnt){
            if(cnt >= Constants.MAX_MSG_CNT_PER_LOGIN){
                return false;
            }
        }
        return true;
    }

    public void clrMsgCount(long uid){
        messageCntMap.put(uid, 0);
    }

    public void incForwardedMsgCount(){
        forwardedMsgCount.getAndIncrement();
    }

    public long getForwardedMsgCount(){
        return forwardedMsgCount.get();
    }

    public void clrForwardedMsgCount(){
        boolean success = forwardedMsgCount.compareAndSet(forwardedMsgCount.get(), 0L);
        while(!success) success = forwardedMsgCount.compareAndSet(forwardedMsgCount.get(), 0L);
    }

    public void incValidLoginCount(){
        validLoginCount.getAndIncrement();
    }

    public long getValidLoginCount(){
       return validLoginCount.get();
    }

    public void clrValidLoginCount(){
        boolean success = validLoginCount.compareAndSet(validLoginCount.get(), 0L);
        while(!success) success = validLoginCount.compareAndSet(validLoginCount.get(), 0L);
    }

    public void incInvalidLoginCount(){
        invalidLoginCount.getAndIncrement();
    }

    public long getInvalidLoginCount(){
        return invalidLoginCount.get();
    }

    public void clrInvalidLoginCount(){
        boolean success = invalidLoginCount.compareAndSet(invalidLoginCount.get(), 0L);
        while(!success) success = invalidLoginCount.compareAndSet(invalidLoginCount.get(), 0L);
    }

    public void incReceivedMsgCount(){
        receivedMsgCount.getAndIncrement();
    }

    public long getReceivedMsgCount(){
        return receivedMsgCount.get();
    }

    public void clrReceivedMsgCount(){
        boolean success = receivedMsgCount.compareAndSet(receivedMsgCount.get(), 0L);
        while(!success) success = receivedMsgCount.compareAndSet(receivedMsgCount.get(), 0L);
    }

    public void incIgnoredMsgCount(){
        ignoredMsgCount.getAndIncrement();
    }

    public long getIgnoredMsgCount(){
       return ignoredMsgCount.get();
    }

    public void clrIgnoredMsgCount(){
        boolean success = ignoredMsgCount.compareAndSet(ignoredMsgCount.get(), 0L);
        while(!success) success = ignoredMsgCount.compareAndSet(ignoredMsgCount.get(), 0L);
    }

}
