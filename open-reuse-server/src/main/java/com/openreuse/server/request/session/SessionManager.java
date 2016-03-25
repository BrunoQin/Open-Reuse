package com.openreuse.server.request.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/25/16.
 */
public class SessionManager {
    private SessionManager(){}
    private static class Singleton {
        private static SessionManager instance = new SessionManager();
    }
    public static SessionManager getInstance(){
        return Singleton.instance;
    }

    private Map<Long, Channel> sessionMap = new ConcurrentHashMap<Long, Channel>(128);

    public Channel getSession(long clientId){
        return sessionMap.get(clientId);
    }

}
