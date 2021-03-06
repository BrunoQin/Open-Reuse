package com.openreuse.common.license.notify;

import com.openreuse.common.license.License;
import com.openreuse.common.license.auth.AuthManager;
import com.openreuse.common.license.store.LicenseStore;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kimmin on 4/13/16.
 */
public class NotifyService {

    private static DelayQueue<DelayedNotify> queue = new DelayQueue<>();
    private static AtomicBoolean runFlag = new AtomicBoolean(true);
    private static LicenseStore store = null;

    private static Thread pullerThread;

    public static void setLicenseStore(LicenseStore _store){
        store = _store;
        startPuller();
    }

    public static void unsetLicenseStore(LicenseStore _store){
        try{
            stopPuller();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
        store = _store;
    }

    public static void startPuller(){
        /** DCL **/
        if(pullerThread == null){
            synchronized (NotifyService.class){
                if(pullerThread == null){
                    pullerThread = new Thread(new NotifyPuller());
                    pullerThread.start();
                }
            }
        }
    }

    public static void stopPuller() throws InterruptedException {
        if(pullerThread != null) {
            runFlag.getAndSet(false);
            pullerThread.join();
        }
    }

    public static void provideNotify(License license, long nanosecond){
        DelayedNotify notify = new DelayedNotify(license, nanosecond);
        queue.offer(notify);
    }


    private static class NotifyPuller implements Runnable{

        public void run(){
            while(runFlag.get()){
                try{
                    DelayedNotify notify = queue.poll(1000, TimeUnit.MILLISECONDS);
                    if(notify == null){
                        try{
                            Thread.currentThread().sleep(1000);
                        }catch (Throwable e){
                            e.printStackTrace();
                        }
                        continue;
                    }
                    License license = notify.getLicense();
                    store.expireLicense(license);
                    AuthManager.getInstance().killProcessed(license);
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        }

    }

}
