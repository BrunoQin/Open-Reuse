package com.openreuse.server.request.session;

import com.openreuse.server.misc.Constants;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/25/16.
 */
public class SessionManager {
    private SessionManager(){
        initTimer();
    }
    private static class Singleton {
        private static SessionManager instance = new SessionManager();
    }
    public static SessionManager getInstance(){
        return Singleton.instance;
    }

    /** Session Map & IdCacheMap **/
    private Map<Long, Channel> sessionMap = new ConcurrentHashMap<Long, Channel>(128);
//    private Map<String, Long> usrIdMap = new ConcurrentHashMap<String, Long>();

    public Channel getSession(long clientId){
        return sessionMap.get(clientId);
    }

    /** Timer for refreshing usrIdMap & sessionMap **/
    private Timer refreshUsrIdMapTimer;

    private void initTimer(){
        refreshUsrIdMapTimer = new Timer();
        refreshUsrIdMapTimer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 0, Constants.REFRESH_ID_CACHE_INTERVAL_MILLIS);
    }

}
