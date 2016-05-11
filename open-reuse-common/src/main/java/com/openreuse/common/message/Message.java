package com.openreuse.common.message;

import java.io.Serializable;

/**
 * Created by kimmin on 3/23/16.
 */
public class Message implements Serializable{

    public Message(){}

    public Message(MessageType type, Reserved reserved, String body, String from, String to){
        this.type = type;
        this.reserved = reserved;
        this.body = body;
        this.from = from;
        this.to = to;
    }

    private MessageType type;
    private Reserved reserved;
    private String body;
    private String from;
    private String to;


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Reserved getReserved() {
        return reserved;
    }

    public void setReserved(Reserved reserved) {
        this.reserved = reserved;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
