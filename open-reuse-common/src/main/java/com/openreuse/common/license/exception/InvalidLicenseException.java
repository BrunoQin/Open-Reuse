package com.openreuse.common.license.exception;

import com.openreuse.common.license.License;
/**
 * Created by kimmin on 4/14/16.
 */
public class InvalidLicenseException extends Exception {
    public InvalidLicenseException(License license){
        this.license = license;
    }
    private License license = null;
    public License getInvalidLicense(){
        return this.license;
    }
}
