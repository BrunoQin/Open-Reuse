package com.openreuse.client.api;

import com.openresure.client.ClientAgent;
import com.openresure.client.service.ConnectionMgmtService;
import com.openresure.client.service.MessageSendingService;
import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.builder.MessageBuilder;
import io.netty.channel.MessageSizeEstimator;
import org.junit.Test;

/**
 * Created by min.jin on 2016/3/28.
 */
public class TestAPI {

    @Test
    public void testValidate(){
        assert ClientAgent.loginValidate("127.0.0.1", "jinmin", "123456");
    }

    @Test
    public void testRegister(){
//        assert ClientAgent.
    }

    @Test
    public void testSend(){
        Message message = MessageBuilder.messageBuilder()
                .setBody("jinmin")
                .setFrom("fromJinmin")
                .setTo("SERVER")
                .setType(MessageType.REDO_LOGIN_MESSAGE)
                .build();
        MessageSendingService.getInstance().provideMessage(message);
        try{
            ConnectionMgmtService.getInstance().latch.await();
//            while(ConnectionMgmtService.getInstance().connThread == null);
//            ConnectionMgmtService.getInstance().connThread.join();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        boolean b = ClientAgent.loginValidate("127.0.0.1", "Bruno", "qinbo");
//        ClientAgent.registerValidate("127.0.0.1", "jinmin", "jinmin");
        if(b){
            ClientAgent.sendTextMessage("Bruno", "hello world");
        }
    }

}
