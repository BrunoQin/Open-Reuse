package com.openresure.common.message;

/**
 * Created by kimmin on 3/23/16.
 */

public enum MessageType {

    LOGIN_MESSAGE("login"),
    LOGOUT_MESSAGE("logout"),
    TEXT_MESSAGE("text"),
    HEARTBEAT_MESSAGE("heartbeat");

    final String type;

    MessageType(String type){
        this.type = type;
    }

}
