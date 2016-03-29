package com.openreuse.server.test.validate;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import com.openreuse.server.request.route.LoginRoute;
import com.openreuse.server.request.route.RegisterRoute;
import org.junit.Test;

/**
 * Created by Bruno on 16/3/29.
 */
public class RouteTest {

    @Test
    public void TestLoginRoute(){
        LoginRoute loginRoute = new LoginRoute();
        Message message = MessageBuilder.messageBuilder()
                .setBody("Bruno" + "\n" + "qinbo")
                .setType(MessageType.LOGIN_MESSAGE)
                .setFrom("Bruno")
                .build();
        loginRoute.route(message);
    }

    @Test
    public void TestTextRoute(){
        LoginRoute loginRoute = new LoginRoute();
        Message message = MessageBuilder.messageBuilder()
                .setBody("Bruno" + "\n" + "qinbo")
                .setType(MessageType.TEXT_MESSAGE)
                .setFrom("Bruno")
                .build();
        loginRoute.route(message);
    }

    @Test
    public void TestLogoutRoute(){
        LoginRoute loginRoute = new LoginRoute();
        Message message = MessageBuilder.messageBuilder()
                .setBody("Bruno" + "\n" + "qinbo")
                .setType(MessageType.LOGOUT_MESSAGE)
                .setFrom("Bruno")
                .build();
        loginRoute.route(message);
    }

    @Test
    public void TestRegisterRoute(){
        RegisterRoute registerRoute = new RegisterRoute();
        Message message = MessageBuilder.messageBuilder()
                .setBody("Tony" + "\n" + "xiaopang")
                .setType(MessageType.REGISTER_MESSAGE)
                .setFrom("Bruno")
                .build();
        registerRoute.route(message);
    }

}
