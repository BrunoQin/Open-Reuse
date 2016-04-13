package com.openreuse.common.license;

import com.openreuse.common.license.auth.AuthManager;
import com.openreuse.common.license.auth.Processable;
import com.openreuse.common.license.auth.Processed;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kimmin on 4/12/16.
 */
public class LicenseManager {
    private LicenseManager(){}
    private static class Singleton {
        private static LicenseManager instance = new LicenseManager();
    }
    public static LicenseManager getInstance(){
        return Singleton.instance;
    }


    public Processed decorateWithLicense(Processable processable, License license){
        LicenseStatus status = AuthManager.getInstance().checkLicenseStatus(license);
        if(LicenseStatus.ALLOWED == status){
            /** Too sleepy.. continue tomorrow **/
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
