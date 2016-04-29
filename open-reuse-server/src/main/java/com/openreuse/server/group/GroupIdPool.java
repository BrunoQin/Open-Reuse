package com.openreuse.server.group;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by kimmin on 4/29/16.
 */
public final class GroupIdPool {

    private static List<Long> groupIdList = new CopyOnWriteArrayList<>();

    static{
        /** Initialize with maximum 10000 available groups **/
        for(int i=0; i<10000; i++){
            groupIdList.add(new Integer(i).longValue());
        }
    }

    /** Allocate a group id, if no available left return -1 **/
    public static long allocateGroupId(){
        if(!groupIdList.isEmpty()) return -1;
        long gid = groupIdList.get(0);
        groupIdList.remove(0);
        return gid;
    }
    /** Just cancel the group id, and make available in the pool again **/
    public static void cancelGroupId(long gid){
        groupIdList.add(gid);
    }
}
