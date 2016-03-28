package com.openreuse.server.response;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by kimmin on 3/25/16.
 */

public class ResponseHelper {
    /** Pre-defined message content **/
    public final static Message OK_RESP_MESSAGE = new Message(MessageType.RESP_OK,
            new Reserved("null"), "MESSAGE RECEIVED SUCCESS!", "SERVER");

    public final static Message ERROR_RESP_MESSAGE = new Message(MessageType.RESP_INTERNAL_ERROR,
            new Reserved("null"), "MESSAGE REJECTED BECAUSE OF INTERNAL SERVER ERROR", "SERVER");
    public final static Message REDO_LOGIN_RESP_MESSAGE = new Message(MessageType.REDO_LOGIN_MESSAGE,
            new Reserved("null"), "MESSAGE MORE THAN 100 TIMES! PLEASE REDO LOGIN", "SERVER");

    public static byte[] BYTE_OK_RESP_MESSAGE;
    public static byte[] BYTE_ERROR_RESP_MESSAGE;
    public static byte[] BYTE_REDO_LOGIN_RESP_MESSAGE;

    static{
        try{
            BYTE_OK_RESP_MESSAGE = new ObjectMapper().writeValueAsBytes(OK_RESP_MESSAGE);
            BYTE_ERROR_RESP_MESSAGE = new ObjectMapper().writeValueAsBytes(ERROR_RESP_MESSAGE);
            BYTE_REDO_LOGIN_RESP_MESSAGE = new ObjectMapper().writeValueAsBytes(REDO_LOGIN_RESP_MESSAGE);
        }catch (Throwable e){
            BYTE_OK_RESP_MESSAGE = null;
            BYTE_ERROR_RESP_MESSAGE = null;
            System.exit(1);
            e.printStackTrace();
        }
    }

}
