package com.openreuse.common.license;

import com.openreuse.common.license.auth.Auth;
import com.openreuse.common.license.auth.AuthManager;
import com.openreuse.common.license.auth.Processable;
import com.openreuse.common.license.auth.Processed;
import com.openreuse.common.license.notify.NotifyService;
import com.openreuse.common.license.store.LicenseStore;
import com.openreuse.common.license.store.LocalFileLicenseStore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kimmin on 4/12/16.
 */
public class LicenseManager {

    private LicenseManager(){
        this.store = new LocalFileLicenseStore();
        AuthManager.getInstance().setLicenseStore(store);
        NotifyService.setLicenseStore(store);
    }
    private static class Singleton {
        private static LicenseManager instance = new LicenseManager();
    }
    public static LicenseManager getInstance(){
        return Singleton.instance;
    }

    private LicenseStore store = null;

    public Processed decorateWithLicense(Processable processable){
        LicenseStatus status = AuthManager.getInstance().checkLicenseStatus(
                processable.retrieveLicense());
        if(LicenseStatus.ALLOWED == status){
            Auth auth = new Auth(processable);
            return auth;
        }
        return null;
    }

    private boolean bWork = true;

    public void setEnableWork(boolean work){
        if(work){
            /** Enable license distribution **/

        }else{
            /** Disable license distribution **/

        }
    }

}
