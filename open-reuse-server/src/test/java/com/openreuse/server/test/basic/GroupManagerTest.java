package com.openreuse.server.test.basic;

import com.openreuse.server.group.Group;
import com.openreuse.server.group.GroupManager;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jasmine on 16/5/14.
 */
public class GroupManagerTest {
    @Test
    public void testGetGroupUserMapping(){
        GroupManager groupManager = GroupManager.getInstance();
        List list = groupManager.getUserGroupMapping("Jasmine");
        assertEquals(list==null,true);

    }

    @Test
    public void testAddUserGroupMapping(){
        GroupManager groupManager = GroupManager.getInstance();
        List<Group> list = groupManager.getUserGroupMapping("Jasmine");
        assertEquals(list==null,true);

        groupManager.addUserGroupMapping("Jasmine", new Group(1));

        list = groupManager.getUserGroupMapping("Jasmine");
        assertEquals(list.get(0).groupId,1);

    }
}
