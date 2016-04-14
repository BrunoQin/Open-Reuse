package com.openreuse.common.persist;

import com.openreuse.common.license.License;
import com.openreuse.common.license.auth.Processable;

/**
 * Created by kimmin on 4/14/16.
 */
public class SimpleProcess implements Processable {
    public SimpleProcess(License license){
        this.license = license;
    }
    private License license = null;
    public void process(){
        System.out.println("PROCESS SUCCESS!");
    }
    public License retrieveLicense(){
        return this.license;
    }
    public void bindLicense(License license){
        this.license = license;
    }
}
