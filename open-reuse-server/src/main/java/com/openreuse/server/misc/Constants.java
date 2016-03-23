package com.openreuse.server.misc;

/**
 * Created by kimmin on 3/20/16.
 */
public class Constants {

    /** App Constants **/
    public final static long INVALID_CLIENT_ID = ~0;
    public final static long INVALID_MESSAGE_ID = ~0;
    public final static long INVALID_DELAY_VALUE = 0;
    public final static int THROTTLE_WORK_NUMBER = 2;
    public final static int SERVER_PORT = 30000;

    /** Time Def Constants **/
    public final static long MILLIS_PER_SECOND = 1000;
    public final static long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
    public final static long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    public final static long MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
    public final static long NANOS_PER_MILLI = 1000 * 1000;

}
