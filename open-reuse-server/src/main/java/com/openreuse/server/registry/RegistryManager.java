package com.openreuse.server.registry;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.pojo.notify.DelayedNotify;
import com.openreuse.server.throttle.ThrottleManager;
import com.openreuse.server.validate.Validators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/20/16.
 */

public class RegistryManager {
    private RegistryManager(){}
    private static class Singleton {
        private static RegistryManager instance = new RegistryManager();
    }
    public static RegistryManager getInstance(){
        return Singleton.instance;
    }

    /** Maybe there should be a upper-bound limitation here? **/
    private Map<Long, Integer> loginCountMap = new ConcurrentHashMap<Long, Integer>();

    public Integer getLoginCountByID(long clientID){
        if (loginCountMap.containsKey(clientID)){
            return loginCountMap.get(clientID);
        }else
            return -1;

    }

    /** Too many login request may cause OOM here **/
    public void registerLogin(long clientID){
        assert Validators.validateClientID(clientID);
        if(loginCountMap.containsKey(clientID)){
            Integer currentCount = loginCountMap.get(clientID);
            currentCount = currentCount + 1;
            loginCountMap.put(clientID, currentCount);
        }else{
            loginCountMap.put(clientID, 1);
        }
        /** Produce count down task **/
        DelayedNotify notify = new DelayedNotify(clientID, Constants.MILLIS_PER_MINUTE
                * Constants.NANOS_PER_MILLI);
        ThrottleManager.getInstance().notity(notify);
    }

    public boolean checkLogin(long clientID){
        assert Validators.validateClientID(clientID);
        if(loginCountMap.containsKey(clientID)){
            Integer currentCount = loginCountMap.get(clientID);
            if(currentCount > 3) return false;
            else return true;
        }else{
            /** Do nothing here **/
            return true;
        }
    }

    public void countDownLogin(long clientID){
        assert Validators.validateClientID(clientID);
        Integer currentCount = loginCountMap.get(clientID);
        if(currentCount == null) return;
        if(currentCount > 1){
            currentCount = currentCount - 1;
            loginCountMap.put(clientID, currentCount);
        }else{
            /** To save space **/
            loginCountMap.remove(clientID);
        }
    }


}
