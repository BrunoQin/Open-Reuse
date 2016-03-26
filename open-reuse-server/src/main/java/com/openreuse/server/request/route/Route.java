package com.openreuse.server.request.route;

import com.openreuse.common.message.Message;

import java.util.concurrent.Future;

/**
 * Created by kimmin on 3/25/16.
 */
public interface Route {
    boolean route(Message message);
}
