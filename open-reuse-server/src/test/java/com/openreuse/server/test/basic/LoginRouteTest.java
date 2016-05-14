package com.openreuse.server.test.basic;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import com.openreuse.server.group.Group;
import com.openreuse.server.group.GroupManager;
import com.openreuse.server.request.route.LoginRoute;
import org.junit.Test;

/**
 * Created by Jasmine on 16/5/14.
 */
public class LoginRouteTest {
    @Test
    public void testRoute(){
        Message loginMessage = MessageBuilder.messageBuilder().setFrom("Jasmine").setBody("Jasmine"+"\n"+"1234").setType(MessageType.LOGIN_MESSAGE).build();
        LoginRoute route = new LoginRoute();
        GroupManager groupManager = GroupManager.getInstance();
        Group groupToAdd = new Group(1);
        groupToAdd.userList.add("Bruno");
        groupManager.addUserGroupMapping("Jasmine",groupToAdd);

        boolean result = route.route(loginMessage);

        System.out.print(result);
    }
}
