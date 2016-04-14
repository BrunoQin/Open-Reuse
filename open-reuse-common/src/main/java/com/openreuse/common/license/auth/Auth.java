package com.openreuse.common.license.auth;

import com.openreuse.common.license.License;
import com.openreuse.common.license.LicenseStatus;
import com.openreuse.common.license.exception.InvalidLicenseException;

/**
 * Created by kimmin on 4/14/16.
 */
public class Auth implements Processed {
    public Auth(Processable processable){
        License license = processable.retrieveLicense();
        this.license = license;
        this.processable = processable;
    }

    private License license = null;
    private boolean bLive = true;
    private Processable processable = null;

    public void suicide(){
        bLive = false;
    }

    public void process() throws InvalidLicenseException{
        if(!bLive && AuthManager.getInstance().checkLicenseStatus(license) != LicenseStatus.ALLOWED){
            throw new InvalidLicenseException(license);
        }else{
            this.processable.process();
        }
    }

    public License getLicense(){
        return this.processable.retrieveLicense();
    }

}
