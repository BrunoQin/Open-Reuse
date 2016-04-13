package com.openreuse.common.license;

/**
 * Created by kimmin on 4/13/16.
 */
public enum LicenseStatus {

    ALLOWED(0),
    BANNED(1),
    EXPIRED(2),
    UNREGISTERED(3);

    private int status;

    LicenseStatus(int status){
        this.status = status;
    }

}
