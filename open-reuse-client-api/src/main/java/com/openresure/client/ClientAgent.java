package com.openresure.client;

import com.openresure.client.config.ConfigManager;
import com.openresure.client.group.ClientGroupManager;
import com.openresure.client.listener.MessageListener;
import com.openresure.client.listener.ValidateLoginListener;
import com.openresure.client.listener.ValidateRegisterListener;
import com.openresure.client.service.ConnectionMgmtService;
import com.openresure.client.service.MessageNotifyService;
import com.openresure.client.service.MessageSendingService;
import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;
import com.openreuse.common.message.builder.MessageBuilder;
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;

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
    public static boolean registerValidate(String ipAddr, String username, String password){
        ConfigManager.getInstance().setUsrFrom(username);
        boolean connSuccess = ConnectionMgmtService.getInstance().doConnect(ipAddr);
        ConfigManager.getInstance().registername = username;
        Message message = MessageBuilder.messageBuilder()
                .setBody(username + "\n" + password)
                .setType(MessageType.REGISTER_MESSAGE)
                .setFrom(username)
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .build();
        ConfigManager.getInstance().registerSuccess.compareAndSet(
                ConfigManager.getInstance().registerSuccess.get(), false
        );
        MessageSendingService.getInstance().provideMessage(message);
        long now = System.currentTimeMillis();
        while(System.currentTimeMillis() < now + 10 * 1000){
            if(ConfigManager.getInstance().registerSuccess.get()){
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
        ConfigManager.getInstance().unsetLogined(username);
        System.out.println("unset");
        MessageSendingService.getInstance().provideMessage(message);
        /** Wait for response **/
        long now = System.currentTimeMillis();
         while(System.currentTimeMillis() < now + 5*1000){
            if(ConfigManager.getInstance().isLogined(username)){
                System.out.println("success");
                return true;
            }
        }
        System.out.print("failure");
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

    public static void sendGroupMessage(String username, String content, String groupName){
        long gid = ClientGroupManager.getInstance().getGroupId(groupName);
        if(gid == -1) return;
        Message message = MessageBuilder.messageBuilder()
                .setBody(content)
                .setFrom(username)
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .setType(MessageType.TEXT_MESSAGE_GROUP)
                .setReserved(new Reserved(new Long(gid).toString()))
                .build();
        MessageSendingService.getInstance().provideMessage(message);
    }

    public static long registerGroup(String username, String groupName, String[] memberNames){
        /** Return -1 if register failed **/
        StringBuilder sb = new StringBuilder();
        for(String memberName: memberNames){
            sb.append(memberName + "\n");
        }
        Message message = MessageBuilder.messageBuilder()
                .setType(MessageType.REGISTER_GROUP_MESSAGE)
                .setBody(sb.toString())
                .setFrom(username)
                .setReserved(new Reserved(groupName))
                .setTo(ConfigManager.getInstance().getCurrentServerAddr())
                .build();
        MessageSendingService.getInstance().provideMessage(message);
        long now = System.currentTimeMillis();
        /** Wait at most 3 seconds for Server's response **/
        while(System.currentTimeMillis() <= now + 3 * 1000){
            long gid = ClientGroupManager.getInstance().getGroupId(groupName);
            if(gid != -1) return gid;
            try{
                Thread.currentThread().sleep(500);
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
        /** Failed **/
        return -1;
    }


}
