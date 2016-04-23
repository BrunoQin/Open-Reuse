package com.openreuse.common.logging.queue;

import com.openreuse.common.message.Message;

/**
 * Created by kimmin on 4/22/16.
 */
public interface IMessageQueue {
    Message getMessage();
    void pushMessage(Message message);
}
