package com.openreuse.server.throttle;


import com.HaroldLIU.LicenseManager;
import com.openreuse.common.persist.LocalPersistHelper;
import com.openreuse.server.misc.Constants;
import wheellllll.performance.LogUtils;
import wheellllll.performance.PerformanceManager;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

;

/**
 * Created by kimmin on 3/26/16.
 */

public class ThrottleStatsManager {

    public PerformanceManager performanceManager;
    private LicenseManager licenseManager;


    private void initPM(){
        performanceManager = new PerformanceManager();
        LogUtils.setLogPrefix("Test");
        LogUtils.setLogPath("./logging");
        performanceManager.setPeriod(60);
        performanceManager.setTimeUnit(TimeUnit.SECONDS);
        performanceManager.setInitialDelay(1);
        performanceManager.addIndex("LoginSuccess");
        performanceManager.addIndex("LoginFailed");
        performanceManager.addIndex("Message");
        performanceManager.addIndex("ForwardedMessage");
        performanceManager.addIndex("ReceivedMessage");
        performanceManager.addIndex("IgnoredMessage");
        performanceManager.start();
    }

    private void initLicense(){
        licenseManager = new LicenseManager();
        licenseManager.CapacityInit(100, 0);
    }

    private ThrottleStatsManager(){
        initPM();
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
        //!!!Bug 会有空指针
        //performanceManager.updateIndex("Message",cnt.intValue());
    }

    public boolean checkMsgCount(long uid){
        Integer cnt = messageCntMap.get(uid);
        if(null != cnt){
            if(!licenseManager.CapacityCheck()){
                return false;
            }
        }
        return true;
    }

    public void clrMsgCount(long uid){
        messageCntMap.put(uid, 0);
        performanceManager.removeIndex("Message");
    }

    public void incForwardedMsgCount(){
        forwardedMsgCount.getAndIncrement();
        performanceManager.updateIndex("ForwardedMessage",1);
    }

    public long getForwardedMsgCount(){
        return forwardedMsgCount.get();
    }

    public void clrForwardedMsgCount(){
        boolean success = forwardedMsgCount.compareAndSet(forwardedMsgCount.get(), 0L);
        while(!success) success = forwardedMsgCount.compareAndSet(forwardedMsgCount.get(), 0L);
        performanceManager.removeIndex("ForwardedMessage");
    }

    public void incValidLoginCount(){
        validLoginCount.getAndIncrement();
        performanceManager.updateIndex("LoginSuccess",1);
    }

    public long getValidLoginCount(){
       return validLoginCount.get();
    }

    public void clrValidLoginCount(){
        boolean success = validLoginCount.compareAndSet(validLoginCount.get(), 0L);
        while(!success) success = validLoginCount.compareAndSet(validLoginCount.get(), 0L);
        performanceManager.removeIndex("LoginSuccess");
    }

    public void incInvalidLoginCount(){
        invalidLoginCount.getAndIncrement();
        performanceManager.updateIndex("LoginFailed",1);
    }

    public long getInvalidLoginCount(){
        return invalidLoginCount.get();
    }

    public void clrInvalidLoginCount(){
        boolean success = invalidLoginCount.compareAndSet(invalidLoginCount.get(), 0L);
        while(!success) success = invalidLoginCount.compareAndSet(invalidLoginCount.get(), 0L);
        performanceManager.removeIndex("LoginFailed");
    }

    public void incReceivedMsgCount(){
        receivedMsgCount.getAndIncrement();
        performanceManager.updateIndex("ReceivedMessage",1);
    }

    public long getReceivedMsgCount(){
        return receivedMsgCount.get();
    }

    public void clrReceivedMsgCount(){
        boolean success = receivedMsgCount.compareAndSet(receivedMsgCount.get(), 0L);
        while(!success) success = receivedMsgCount.compareAndSet(receivedMsgCount.get(), 0L);
        performanceManager.removeIndex("ReceivedMessage");
    }

    public void incIgnoredMsgCount(){
        ignoredMsgCount.getAndIncrement();
        performanceManager.updateIndex("IgnoredMessage",1);
    }

    public long getIgnoredMsgCount(){
       return ignoredMsgCount.get();
    }

    public void clrIgnoredMsgCount(){
        boolean success = ignoredMsgCount.compareAndSet(ignoredMsgCount.get(), 0L);
        while(!success) success = ignoredMsgCount.compareAndSet(ignoredMsgCount.get(), 0L);
        performanceManager.removeIndex("IgnoredMessage");
    }

}
