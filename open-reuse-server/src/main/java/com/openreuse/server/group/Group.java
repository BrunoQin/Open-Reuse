package com.openreuse.server.group;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kimmin on 4/29/16.
 */
public class Group {
    public Group(){}
    public Group(long gid){
        this.groupId = gid;
    }
    public long groupId = -1;
    public List<Long> userList = new LinkedList<>();
}
