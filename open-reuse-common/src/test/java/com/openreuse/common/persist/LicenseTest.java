package com.openreuse.common.persist;

import com.openreuse.common.license.License;
import com.openreuse.common.license.LicenseManager;
import com.openreuse.common.license.auth.AuthManager;
import com.openreuse.common.license.auth.Processed;
import com.openreuse.common.license.exception.InvalidLicenseException;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by kimmin on 4/14/16.
 */
public class LicenseTest {
    @Test
    public void applyForLicenseTest(){
        LicenseManager.getInstance().setEnableWork(true);
        License license = AuthManager.getInstance().applyForLicense(1000, TimeUnit.MILLISECONDS);
        SimpleProcess simpleProcess = new SimpleProcess(license);
        Processed processed = LicenseManager.getInstance().decorateWithLicense(simpleProcess);
        for(int i = 0; i < 5; i++){
            try{
                processed.process();
                Thread.currentThread().sleep(1000);
            }catch (InvalidLicenseException ie){
                ie.printStackTrace();
            }catch (InterruptedException iie){
                iie.printStackTrace();
            }
        }
    }
}
