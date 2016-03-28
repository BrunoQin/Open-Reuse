package com.openreuse.server.request.dispatcher;

import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.server.request.route.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/25/16.
 */
public class RouteDispatcher implements Dispatcher {

    private RouteDispatcher(){
        /** Init route map for dispatcher **/
        routeMap.put(MessageType.LOGIN_MESSAGE, new LoginRoute());
        routeMap.put(MessageType.LOGOUT_MESSAGE, new LogoutRoute());
        routeMap.put(MessageType.REGISTER_MESSAGE, new RegisterRoute());
        routeMap.put(MessageType.TEXT_MESSAGE, new TextRoute());
    }
    private static class Singleton {
        private static RouteDispatcher instance = new RouteDispatcher();
    }
    public static RouteDispatcher getInstance(){
        return Singleton.instance;
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
