package com.openreuse.common.license.store;

import com.openreuse.common.license.License;
import com.openreuse.common.license.LicenseStatus;

import java.sql.Time;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * Created by kimmin on 4/13/16.
 */
public class LocalFileLicenseStore implements LicenseStore {

    public int getRemainingLicense(){
        return 0;
    }

    public License pollLicense(long time, TimeUnit timeUnit){
        long timeInMillis = TimeUnit.MILLISECONDS.convert(time, timeUnit);
        long startMillis = System.currentTimeMillis();
        while(System.currentTimeMillis() < startMillis + timeInMillis){
            /** Try to retrieve a license **/
            Iterator<License> iterator = set.iterator();
            if(iterator.hasNext()){
                License license = iterator.next();
                iterator.remove();
                return license;
            }
        }
        return null;
    }

    public void expireLicense(License license){
        if(!set.contains(license)){
            set.add(license);
        }
    }

    public void initLicense(int initCapacity){
        for(int i=1; i <= initCapacity; i++){
            byte[] bytes = ("" + initCapacity).getBytes();
            License license = new License(bytes);
            set.add(license);
        }
    }

    public boolean licenseExists(License license){
        return set.contains(license);
    }

    private Set<License> set = new CopyOnWriteArraySet<>();


}
