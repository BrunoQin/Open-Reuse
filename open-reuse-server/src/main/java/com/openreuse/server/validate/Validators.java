package com.openreuse.server.validate;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.pojo.notify.DelayedNotify;

/**
 * Created by kimmin on 3/20/16.
 */

public class Validators {

    public static boolean validateNotify(DelayedNotify notify){
        if(notify.getClientId() == Constants.INVALID_CLIENT_ID) return false;
        if(notify.getMilliDelay() == Constants.INVALID_DELAY_VALUE) return false;
        return true;
    }

    public static boolean validateClientID(long clientID){
        if(clientID == Constants.INVALID_CLIENT_ID) return false;
        else return true;
    }

}
