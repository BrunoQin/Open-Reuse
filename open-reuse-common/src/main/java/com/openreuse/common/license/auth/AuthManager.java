package com.openreuse.common.license.auth;

import com.openreuse.common.license.License;
import com.openreuse.common.license.LicenseStatus;
import com.openreuse.common.license.notify.NotifyService;
import com.openreuse.common.license.store.LicenseStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by kimmin on 4/13/16.
 */
public class AuthManager {
    private AuthManager(){}
    private static class Singleton {
        private static AuthManager instance = new AuthManager();
    }
    public static AuthManager getInstance(){
        return Singleton.instance;
    }

    private LicenseStore store = null;
    private Map<License, Processed> map = new ConcurrentHashMap<>();

    public void killProcessed(License license){
        if(map.containsKey(license)){
            Processed processed = map.get(license);
            processed.suicide();
        }
    }

    public void registerProcessed(License license, Processed processed){
        map.put(license, processed);
    }

    public void setLicenseStore(LicenseStore store){
        if(this.store == null) {
            this.store = store;
            store.initLicense(100);
            NotifyService.startPuller();
        }
    }

    public void unsetLicenseStore(){
        if(store != null){
            this.store = null;
        }
    }

    public LicenseStatus checkLicenseStatus(License license){
        if(this.store.licenseExists(license)){
            return LicenseStatus.EXPIRED;
        }else{
            /** Roughly accepted .. **/
            return LicenseStatus.ALLOWED;
        }
    }

    public License applyForLicense(long time, TimeUnit timeUnit){
        License license = this.store.pollLicense(time, timeUnit);
        if(license != null){
            NotifyService.provideNotify(license, TimeUnit.NANOSECONDS.convert(time, timeUnit));
        }
        return license;
    }

}
