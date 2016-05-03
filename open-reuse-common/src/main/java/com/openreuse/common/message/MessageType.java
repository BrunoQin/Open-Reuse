package com.openreuse.common.message;

/**
 * Created by kimmin on 3/23/16.
 */

public enum MessageType {

    LOGIN_MESSAGE("login"),
    LOGOUT_MESSAGE("logout"),
    REGISTER_MESSAGE("register"),
    TEXT_MESSAGE("text"),
    HEARTBEAT_MESSAGE("heartbeat"),
    RESP_OK("response_ok"),
    RESP_INTERNAL_ERROR("response_error"),
    RESP_REFUSE("response_refuse"),
    REDO_LOGIN_MESSAGE("redo_login"),
    TEXT_MESSAGE_GROUP("text_group"),
    REGISTER_GROUP_MESSAGE("register_group"),
    RESP_REGISTER_GROUP_OK("response_register_group");

    final String type;

    MessageType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

}
