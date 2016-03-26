package com.openreuse.server.request.session;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.registry.validateRegistry.UserInfoDao;
import com.openreuse.server.throttle.ThrottleManager;
import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/25/16.
 */
public class SessionManager {

    UserInfoDao userInfoDao = new UserInfoDao();

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
    private Map<String, Long> usrIdMap = new ConcurrentHashMap<String, Long>(128);

    public Channel getSession(long clientId){
        return sessionMap.get(clientId);
    }

    public boolean haveSession(Channel channel){
        return sessionMap.containsValue(channel);
    }

    public void registerSession(long clientId, Channel channel){
        sessionMap.put(clientId, channel);
    }

    public void removeSession(long clientId){
        sessionMap.remove(clientId);
    }

    public Iterator<Map.Entry<Long, Channel>> sessionIterator(){
        return sessionMap.entrySet().iterator();
    }

    public Long getUsrId(String from){
        Long uid = usrIdMap.get(from);
        if(null == uid){
            uid = userInfoDao.getIdbyName(from);
            if(-1 == uid){
                return null;
            }else{
                this.usrIdMap.put(from, uid);
            }
        }
        return uid;
    }

    /** Timer for refreshing usrIdMap & sessionMap **/
    private Timer refreshUsrIdMapTimer;

    private void initTimer(){
        refreshUsrIdMapTimer = new Timer();
        refreshUsrIdMapTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        }, 0, Constants.REFRESH_ID_CACHE_INTERVAL_MILLIS);
    }

    public boolean registerUsr(String username, String password){
        return userInfoDao.addUser(username, password);
    }

}
