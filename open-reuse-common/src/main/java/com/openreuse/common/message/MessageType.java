package com.openreuse.common.message;

/**
 * Created by kimmin on 3/23/16.
 */

public enum MessageType {

    LOGIN_MESSAGE("login"),
    LOGOUT_MESSAGE("logout"),
    TEXT_MESSAGE("text"),
    HEARTBEAT_MESSAGE("heartbeat"),
    RESP_OK("response_ok"),
    RESP_INTERNAL_ERROR("response_error"),
    RESP_REFUSE("response_refuse");

    final String type;

    MessageType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
