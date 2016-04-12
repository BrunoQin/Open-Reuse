package com.openreuse.common.config;

import com.openreuse.common.config.coef.CoefStore;

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

    public static void dumpBasicCoef(){
        /** Do dumps here **/
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

}
