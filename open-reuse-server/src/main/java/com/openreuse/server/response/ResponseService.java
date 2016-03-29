package com.openreuse.server.response;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.openreuse.common.message.Message;
import com.openreuse.server.response.listener.ThrottleStatsListener;

import java.util.concurrent.Executors;

/**
 * Created by min.jin on 2016/3/28.
 */
public class ResponseService {
    private ResponseService(){}
    private static class Singleton {
        private static ResponseService instance = new ResponseService();
    }
    public static ResponseService getInstance(){
        return Singleton.instance;
    }

    private ListeningExecutorService service = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(4)
    );

    public void sendMessage(Message message, String username){
        ListenableFuture future = service.submit(Executors.callable(new UnicastResponseTask(message, username)));
        future.addListener(new ThrottleStatsListener(), service);
    }

    public void sendMessage(Message message){
        ListenableFuture future = service.submit(Executors.callable(new MulticastResponseTask(message)));
        future.addListener(new ThrottleStatsListener(), service);
    }

}
