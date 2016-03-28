package com.openresure.client;

import com.openresure.client.config.ConfigManager;
import com.openresure.client.listener.MessageListener;
import com.openresure.client.listener.ValidateLoginListener;
import com.openresure.client.listener.ValidateRegisterListener;
import com.openresure.client.service.ConnectionMgmtService;
import com.openresure.client.service.MessageNotifyService;
import com.openresure.client.service.MessageSendingService;
import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;

/**
 * Created by min.jin on 2016/3/28.
 */


public class ClientAgent {

    /** Fake Main Entry **/
    public static void main(String[] args){}

    /** Init **/
    static{
        MessageNotifyService.getInstance().registerListener(MessageType.LOGIN_MESSAGE, new ValidateLoginListener());
        MessageNotifyService.getInstance().registerListener(MessageType.REGISTER_MESSAGE,new ValidateRegisterListener());
    }

    /** API Defination **/
    public static void registerListener(MessageType type, MessageListener listener){
        MessageNotifyService.getInstance().registerListener(type, listener);
    }

    public static void configureServer(String ipAddr){
        /** Uhh? **/
        ConnectionMgmtService.getInstance().doConnect(ipAddr);
    }

    /** Blocks more than 5 sec means failure **/
    public static boolean registerValidate(String username, String password){
        Message message = MessageBuilder.messageBuilder()
                .setBody(username + "\n" + password)
                .setType(MessageType.REGISTER_MESSAGE)
                .setFrom(username)
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .build();
        ConfigManager.getInstance().registerSuccess = false;
        MessageSendingService.getInstance().provideMessage(message);
        long now = System.currentTimeMillis();
        while(System.currentTimeMillis() < now + 5*1000){
            if(ConfigManager.getInstance().registerSuccess){
                return true;
            }
        }
        return false;
    }

    /** Blocking more than 5 sec means failure **/
    public static boolean loginValidate(String ipAddr, String username, String password){
        /** Actually building connection **/
        ConfigManager.getInstance().setUsrFrom(username);
        boolean connSuccess = ConnectionMgmtService.getInstance().doConnect(ipAddr);

        Message message = MessageBuilder.messageBuilder()
                .setBody(username + "\n" + password)
                .setFrom(ConfigManager.getInstance().getUsrFrom())
                .setType(MessageType.LOGIN_MESSAGE)
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .build();
        /** Sending Validate Request **/
        MessageSendingService.getInstance().provideMessage(message);
        /** Wait for response **/
        ConfigManager.getInstance().unsetLogined(username);
        long now = System.currentTimeMillis();
         while(System.currentTimeMillis() < now + 5*1000){
            if(ConfigManager.getInstance().isLogined(username)){
                return true;
            }
        }
        return false;
    }

    public static void logout(String username){
        Message message = MessageBuilder.messageBuilder()
                .setBody("null")
                .setFrom(username)
                .setType(MessageType.LOGOUT_MESSAGE)
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .build();
        MessageSendingService.getInstance().provideMessage(message);
        ConfigManager.getInstance().unsetLogined(username);
    }

    public static void sendTextMessage(String username, String content){
        Message message = MessageBuilder.messageBuilder()
                .setBody(content)
                .setFrom(username)
                .setType(MessageType.TEXT_MESSAGE)
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .build();
        MessageSendingService.getInstance().provideMessage(message);
    }
}
