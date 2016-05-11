package com.openreuse.server.response;


/**
 * Created by kimmin on 5/11/16.
 */
public class ResendMsgService {
    private ResendMsgService(){}
    private static class Singleton {
        private static ResendMsgService instance = new ResendMsgService();
    }
    public static ResendMsgService getInstance(){
        return Singleton.instance;
    }

}

