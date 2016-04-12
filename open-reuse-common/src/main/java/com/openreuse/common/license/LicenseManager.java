package com.openreuse.common.license;

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

    private Generator generator = new Generator();

    public void setLicenseCapacity(){
        ;
    }

    public CallerMessage requestLicense(){
        return null;
    }

    public int getRemainingCapacity(){
        return 1;

    }

    private class Generator {
        public CallerMessage generate(String org){
            try{
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                mdInst.update(org.getBytes());
                byte[] newBytes = mdInst.digest();
                return new CallerMessage(newBytes);
            }catch (NoSuchAlgorithmException ne){
                ne.printStackTrace();
                return null;
            }
        }
    }

}
