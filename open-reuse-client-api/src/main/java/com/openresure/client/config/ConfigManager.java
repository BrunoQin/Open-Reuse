package com.openresure.client.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ConfigManager {
    private ConfigManager(){}
    private static class Singleton{
        private static ConfigManager instance = new ConfigManager();
    }
    public static ConfigManager getInstance(){
        return Singleton.instance;
    }

    private String serverAddr = "NO SERVER";
    private Channel channel = null;
    private Lock channelLock = new ReentrantLock();
    private String usrFrom = "NO FROM";
    private ChannelFuture future = null;
    private Map<String, AtomicBoolean> loginStatusMap = new ConcurrentHashMap<String, AtomicBoolean>();

    public boolean registerSuccess = false;
    public String registername = "";

    public String getCurrentServerAddr(){
        return serverAddr;
    }

    public void setCurrentServerAddr(String ipAddr){
        this.serverAddr = ipAddr;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        if(this.channel == null){
            this.channel = channel;
            return;
        }
//        channelLock.lock();
        if(this.channel.equals(channel)) {
//            channelLock.unlock();
            return;
        }else this.channel = channel;
//        channelLock.unlock();
    }


    public Lock getChannelLock() {
        return channelLock;
    }

    public void setChannelLock(Lock channelLock) {
        this.channelLock = channelLock;
    }

    public String getUsrFrom() {
        return usrFrom;
    }

    public void setUsrFrom(String usrFrom) {
        this.usrFrom = usrFrom;
    }

    public boolean isLogined(String username){
        if(loginStatusMap.containsKey(username)){
            return loginStatusMap.get(username).get();
        }else return false;
    }

    public void setLogined(String username){
        loginStatusMap.put(username, new AtomicBoolean(true));
    }

    public void unsetLogined(String username){
        loginStatusMap.put(username, new AtomicBoolean(false));
    }

    public void setChannelFuture(ChannelFuture future){
        this.future = future;
    }

//    public void boostrap(){
//        try {
//            this.future.channel().closeFuture().sync();
//        }catch (Throwable e){
//            e.printStackTrace();
//        }
//    }
}
