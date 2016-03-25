package com.openreuse.server.response;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.common.message.Reserved;

/**
 * Created by kimmin on 3/25/16.
 */

public class ResponseHelper {
    /** Pre-defined message content **/
    public final static Message OK_RESP_MESSAGE = new Message(MessageType.RESP_OK,
            new Reserved("null"), "MESSAGE RECEIVED SUCCESS!", 0L);

    public final static Message ERROR_RESP_MESSAGE = new Message(MessageType.RESP_INTERNAL_ERROR,
            new Reserved("null"), "MESSAGE REJECTED BECAUSE OF INTERNAL SERVER ERROR", 0L);


}
