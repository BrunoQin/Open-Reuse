package com.openreuse.server.request.dispatcher;

import com.openreuse.common.message.Message;

/**
 * Created by kimmin on 3/25/16.
 */
public interface Dispatcher {
    void dispatch(Message message);
}
