package com.openreuse.common.message;

/**
 * Created by kimmin on 3/23/16.
 */

public enum MessageType {

    LOGIN_MESSAGE("login"),
    LOGOUT_MESSAGE("logout"),
    TEXT_MESSAGE("text"),
    HEARTBEAT_MESSAGE("heartbeat"),
    RESP_OK("response");

    final String type;

    MessageType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
