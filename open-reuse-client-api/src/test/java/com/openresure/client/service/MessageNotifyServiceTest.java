package com.openresure.client.service;

import com.openresure.client.config.ConfigManager;
import com.openresure.client.listener.ValidateLoginListener;
import com.openresure.client.listener.ValidateRegisterListener;
import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ryw on 2016/4/6.
 */
public class MessageNotifyServiceTest {

    @Test
    public void testRegisterListener() throws Exception {
        MessageNotifyService.getInstance().registerListener(MessageType.LOGIN_MESSAGE,new ValidateLoginListener());
        MessageNotifyService.getInstance().registerListener(MessageType.REGISTER_MESSAGE,new ValidateRegisterListener());
        assertEquals(2,MessageNotifyService.getInstance().getMap().size());
    }

    @Test
    public void testPublish() throws Exception {
        MessageNotifyService.getInstance().registerListener(MessageType.LOGIN_MESSAGE,new ValidateLoginListener());
        MessageNotifyService.getInstance().registerListener(MessageType.REGISTER_MESSAGE,new ValidateRegisterListener());
        Reserved r=new Reserved();
        Message m=new Message(MessageType.LOGIN_MESSAGE,r,"Wen","Wen","0.0.0.0" );
        MessageNotifyService.getInstance().publish(m);
        assertEquals(true, ConfigManager.getInstance().isLogined("Wen"));
        m.setType(MessageType.REGISTER_MESSAGE);
        ConfigManager.getInstance().registername="Wen";
        MessageNotifyService.getInstance().publish(m);
        assertEquals(true,ConfigManager.getInstance().registerSuccess);
        ///if new other listeners need more test
    }
}