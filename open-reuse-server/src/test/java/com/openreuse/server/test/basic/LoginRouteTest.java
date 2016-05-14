package com.openreuse.server.test.basic;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import com.openreuse.server.group.Group;
import com.openreuse.server.group.GroupManager;
import com.openreuse.server.request.route.LoginRoute;
import org.junit.Test;

import java.util.List;

/**
 * Created by Jasmine on 16/5/14.
 */
public class LoginRouteTest {
    @Test
    public void testRoute(){
        Message loginMessage = MessageBuilder.messageBuilder().setFrom("Jasmine").setBody("Jasmine"+"\n"+"1234").setType(MessageType.LOGIN_MESSAGE).build();
        LoginRoute route = new LoginRoute();
        boolean result = route.route(loginMessage);

        if(result==true){
            List<Group> groupList = GroupManager.getInstance().getUserGroupMapping("Jasmine");
            for(Group group : groupList){
                System.out.print(group.userList.toString());
            }
        }
       else
            System.out.print(result);
    }
}
