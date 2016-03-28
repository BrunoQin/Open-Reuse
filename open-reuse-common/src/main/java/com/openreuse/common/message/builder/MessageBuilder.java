package com.openreuse.common.message.builder;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;

/**
 * Created by min.jin on 2016/3/28.
 */
public class MessageBuilder {
    /** Builder Pattern **/
    private MessageBuilder(){}

    public static MessageBuilder messageBuilder(){
        return new MessageBuilder();
    }

    private Message message = new Message(MessageType.HEARTBEAT_MESSAGE,
            new Reserved("null"),
            "","");

    public Message build(){
        return message;
    }

    public MessageBuilder setBody(String body){
        message.setBody(body);
        return this;
    }

    public MessageBuilder setFrom(String from){
        message.setFrom(from);
        return this;
    }

    public MessageBuilder setType(MessageType type){
        message.setType(type);
        return this;
    }

    public MessageBuilder setReserved(Reserved reserved){
        message.setReserved(reserved);
        return this;
    }


}
