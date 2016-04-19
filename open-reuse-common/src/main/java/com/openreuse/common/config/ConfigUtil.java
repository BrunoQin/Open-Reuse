package com.openreuse.common.config;

import com.openreuse.common.config.coef.AbstractCoef;
import com.openreuse.common.config.coef.CoefStore;
import com.openreuse.common.config.coef.type.CoefType;

import java.io.File;

/**
 * Created by kimmin on 4/11/16.
 */
public class ConfigUtil {

    /** Utility Functions.. **/
    public static void resetBasicCoef(){
        CoefStore.setCoef("max.msg_per_login", 100);
        CoefStore.setCoef("max_json_size", 1024);
        CoefStore.setCoef("server_port", 30000);
        CoefStore.setCoef("dump_stats_interval_millis", 1000 * 60);
    }

    public static void dumpBasicCoef(File jsonFile){
        /** Do dumps here **/
        CoefStore.dumpConfigToFile(jsonFile);
    }

    public static void loadBasicCoef(File jsonFile){
        CoefStore.loadConfigToFile(jsonFile);
    }

    public static void cleanBasicCoef(){
        /** Do cleans here **/
    }

    public static void lockBasicCoef(){
        /** Do locks here **/
    }

    public static void unlockBasicCoef(){
        /** Do unlocks here **/
    }

    public static void setBasicCoef(String key, Integer iV){
        CoefStore.setCoef(key, iV);
    }

    public static void setBasicCoef(String key, String sV){
        CoefStore.setCoef(key, sV);
    }

    public static AbstractCoef getBasicCoef(String key){
        return CoefStore.getCoef(key);
    }




}
