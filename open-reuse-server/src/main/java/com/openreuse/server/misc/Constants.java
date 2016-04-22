package com.openreuse.server.misc;

import conf.Config;

/**
 * Created by kimmin on 3/20/16.
 */
public class Constants {

    public static Config config;

    public Constants() {
        config=new Config();
    }

    /** App Constants **/
    public static long INVALID_CLIENT_ID = ~0;
    public static long INVALID_MESSAGE_ID = ~0;
    public static long INVALID_DELAY_VALUE = 0;
    public static int THROTTLE_WORKER_NUMBER = 2;
    public static int SERVER_PORT = 30000;
    public static int MAX_JSON_SIZE = 1024;
    public static int MAX_MSG_CNT_PER_LOGIN = 100;
    public static int PARSE_WORKER_NUMBER =
            Runtime.getRuntime().availableProcessors() * 2;
    public static long REFRESH_ID_CACHE_INTERVAL_MILLIS = 1000 * 5;
    public static long REDUMP_STATS_INTERVAL_MILLIS = 1000 * 60;
    public static String LOGIN_SUCCESS_INFO = "LOGIN SUCCESS";

    /** Time Def Constants **/
    public static long MILLIS_PER_SECOND = 1000;
    public static long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    public static long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    public static long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
    public static long NANOS_PER_MILLI = 1000 * 1000;

    public static int DUMP_MQ_CAPACITY = 1024;

    public static void update(){
        try{
            config.readFile("config.json");
            Constants.SERVER_PORT = config.getInt("SERVER_PORT");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
