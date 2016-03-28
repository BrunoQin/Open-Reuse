package com.openreuse.server.response;

import com.openreuse.common.message.Message;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by min.jin on 2016/3/28.
 */
public abstract class ResponseTask implements Runnable{

    public ResponseTask(Message message){
        this.message = message;
    }

    private Message message;

    protected static ObjectMapper om = new ObjectMapper();

    public abstract void run();


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
