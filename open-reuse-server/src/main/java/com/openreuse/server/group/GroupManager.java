package com.openreuse.server.group;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kimmin on 4/29/16.
 */
public class GroupManager {
    private GroupManager(){}
    private static class Singleton {
        private static GroupManager instance = new GroupManager();
    }
    public static GroupManager getInstance(){
        return Singleton.instance;
    }

    private Map<Long, Group> groupMap = new LinkedHashMap<>();

    public Group getGroupByGid(long gid){
        return groupMap.get(gid);
    }

    public void enableGroup(long gid, Group group){
        groupMap.put(gid, group);
    }

    public void disableGroup(long gid){
        groupMap.remove(gid);
    }

}
