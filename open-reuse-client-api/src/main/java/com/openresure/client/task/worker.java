package com.openresure.client.task;

import com.openresure.client.action.OpenReuseClient;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Bruno on 16/3/23.
 */
public class Worker {

    private static Worker worker = new Worker();

    public synchronized Worker getWorker(){
        if(worker == null){
            worker = new Worker();
        }
        return worker;
    }

    public void addHandler(OpenReuseClient openReuseClient, ChannelInboundHandlerAdapter channelInboundHandlerAdapter) throws Exception{


    }

}
