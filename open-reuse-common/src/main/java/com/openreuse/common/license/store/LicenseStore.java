package com.openreuse.common.license.store;

import com.openreuse.common.license.License;

import java.util.concurrent.TimeUnit;

/**
 * Created by kimmin on 4/13/16.
 */
public interface LicenseStore {

    int getRemainingLicense();

    License pollLicense(long time, TimeUnit timeUnit);

    void expireLicense(License license);

    void initLicense(int initCapacity);

    boolean licenseExists(License license);

}
