package com.openresure.client.group;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kimmin on 5/3/16.
 */
public class ClientGroupManager {
    private ClientGroupManager(){}
    private static class Singleton {
        private static ClientGroupManager instance;
    }
    public static ClientGroupManager getInstance(){
        return Singleton.instance;
    }

    private Map<String, Long> groupIdMap = new HashMap<>();

    public long getGroupId(String groupName){
        if(this.groupIdMap.containsKey(groupName)){
            return this.groupIdMap.get(groupName);
        }else return -1;
    }

    public void putGroupId(String groupName, long gid){
        this.groupIdMap.put(groupName, gid);
    }

}
