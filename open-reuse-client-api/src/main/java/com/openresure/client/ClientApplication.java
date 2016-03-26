package com.openresure.client;

import com.openresure.client.action.OpenReuseClient;

/**
 * Created by Bruno on 16/3/23.
 */
public class ClientApplication {

    public static void main(String[] args) throws Exception {

        OpenReuseClient openReuseClient = new OpenReuseClient("localhost", 8080);
        openReuseClient.connectServer();
        openReuseClient.sendMessage("hehehehehehe");

    }

}
