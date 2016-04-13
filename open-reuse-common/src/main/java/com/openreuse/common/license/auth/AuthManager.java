package com.openreuse.common.license.auth;

import com.openreuse.common.license.License;
import com.openreuse.common.license.LicenseStatus;
import com.openreuse.common.license.store.LicenseStore;

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

    public void setLicenseStore(LicenseStore store){
        if(this.store == null) {
            this.store = store;
        }
    }

    public void unsetLicenseStore(){
        if(store != null){
            this.store = null;
        }
    }

    public LicenseStatus checkLicenseStatus(License license){
        if(this.store.licenseExists(license)){
            return LicenseStatus.ALLOWED;
        }else{
            /** Roughly deny.. **/
            return LicenseStatus.EXPIRED;
        }
    }

}
