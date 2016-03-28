package com.openresure.client.config;

import com.openreuse.common.persist.LocalPersistHelper;
import com.openreuse.server.misc.Constants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ConfigManager {
    private ConfigManager(){
        dumpTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                refreshStatsToFile();
            }
        }, Constants.MILLIS_PER_MINUTE);
    }
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

    private Timer dumpTimer = new Timer();

    public static AtomicInteger loginSuccessCount = new AtomicInteger(0);
    public static AtomicInteger loginFailureCount = new AtomicInteger(0);
    public static AtomicInteger receivedCount = new AtomicInteger(0);
    public static AtomicInteger sendCount = new AtomicInteger(0);

    public static Map<String, Integer> statsMap = new ConcurrentHashMap<String, Integer>();
    static{
        statsMap.put("loginSuccessCount", loginSuccessCount.get());
        statsMap.put("loginFailureCount", loginFailureCount.get());
        statsMap.put("receivedCount", receivedCount.get());
        statsMap.put("sendCount", sendCount.get());
    }
    public static void refreshStatsToFile(){

        statsMap.put("loginSuccessCount", loginSuccessCount.get());
        statsMap.put("loginFailureCount", loginFailureCount.get());
        statsMap.put("receivedCount", receivedCount.get());
        statsMap.put("sendCount", sendCount.get());
        LocalPersistHelper.persistMapToLocal(statsMap, new File("clientStats.json"));
    }

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
