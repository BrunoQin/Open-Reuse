package com.openreuse.server.group;

import java.util.*;

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

    private Map<String, List<Group>> userGroupMap = new LinkedHashMap<>();

    public Group getGroupByGid(long gid){
        return groupMap.get(gid);
    }

    public void enableGroup(long gid, Group group){
        groupMap.put(gid, group);
    }

    public void disableGroup(long gid){
        groupMap.remove(gid);
    }

    public void addUserGroupMapping(String username, Group group){
        if(!userGroupMap.containsKey(username)){
            List<Group> list = new LinkedList<>();
            list.add(group);
            userGroupMap.put(username, list);
        }else{
            List<Group> list = userGroupMap.get(username);
            list.add(group);
        }
    }

    public List<Group> getUserGroupMapping(String username){
        return userGroupMap.get(username);
    }

}
