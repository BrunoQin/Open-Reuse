package com.openreuse.window.listener;


import com.openreuse.window.body.SendMessageWindow;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jasmine on 16/4/7.
 */

public class ListenerTest {

    SendMessageWindow sendMessageWindow;
    @Before
    public void beforeTests(){
        sendMessageWindow = new SendMessageWindow("Testing","127.0.0.1");
        sendMessageWindow.setListModel("Kimmin");
    }

    @Test
    public void LoginListenerTest(){
        //SendMessageWindow sendMessageWindow = Mockito.mock(SendMessageWindow.class);
        //Mockito.when(sendMessageWindow.getOnlineClients()).thenReturn("Validated");
        //SendMessageWindow sendMessageWindow = new SendMessageWindow("Testing","127.0.0.1");
        LoginListener loginListener = new LoginListener(sendMessageWindow);
        loginListener.onMessageArrive("Jasmine");
        System.out.print(sendMessageWindow.getClient(1));
    }

    @Test
    public void LogoutListenerTest(){

        LogoutListener logoutListener = new LogoutListener(sendMessageWindow);
        logoutListener.onMessageArrive("Kimmin");
        System.out.print(sendMessageWindow.listModel.getSize());
    }

    @Test
    public void RedoLoginListenerTest(){

        RedoLoginListener redoLoginListener = new RedoLoginListener(sendMessageWindow);
        redoLoginListener.onMessageArrive("redo_login");

    }

}
