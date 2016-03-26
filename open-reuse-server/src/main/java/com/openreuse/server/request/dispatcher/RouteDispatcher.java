package com.openreuse.server.request.dispatcher;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.server.request.route.Route;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/25/16.
 */
public class RouteDispatcher implements Dispatcher {

    public RouteDispatcher(){
        /** Init route map for dispatcher **/
    }

    private Map<MessageType, Route> routeMap = new ConcurrentHashMap<MessageType, Route>();

    public void dispatch(Message message){

        /** EX - Tunnel for fast registration **/
        Route route = routeMap.get(message.getType());
        if(null == route){
            /** That's a pity. **/
        }else{
            /** Do something 4 real **/
            route.route(message);
        }
    }

}
