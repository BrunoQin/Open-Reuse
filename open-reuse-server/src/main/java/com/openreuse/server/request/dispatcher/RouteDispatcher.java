package com.openreuse.server.request.dispatcher;

import com.openreuse.common.logging.queue.IMessageQueue;
import com.openreuse.common.logging.queue.MessageQueueImpl;
import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;
import com.openreuse.server.misc.Constants;
import com.openreuse.server.request.route.*;
import com.openreuse.server.request.session.SessionManager;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 3/25/16.
 */
public class RouteDispatcher implements Dispatcher {

    private static IMessageQueue mq = new MessageQueueImpl(Constants.DUMP_MQ_CAPACITY);

    private RouteDispatcher(){
        /** Init route map for dispatcher **/
        routeMap.put(MessageType.LOGIN_MESSAGE, new LoginRoute());
        routeMap.put(MessageType.LOGOUT_MESSAGE, new LogoutRoute());
        routeMap.put(MessageType.REGISTER_MESSAGE, new RegisterRoute());
        routeMap.put(MessageType.TEXT_MESSAGE, new TextRoute());
        routeMap.put(MessageType.TEXT_MESSAGE_GROUP, new GroupRoute());
        routeMap.put(MessageType.REGISTER_GROUP_MESSAGE, new RegisterGroupRoute());
    }
    private static class Singleton {
        private static RouteDispatcher instance = new RouteDispatcher();
    }
    public static RouteDispatcher getInstance(){
        return Singleton.instance;
    }

    private Map<MessageType, Route> routeMap = new ConcurrentHashMap<MessageType, Route>();

    public static Message undumpMessageFromQueue(){
        return mq.getMessage();
    }

    public void dispatch(Message message){
        /** Dump the message into MQ **/
        mq.pushMessage(message);
        /** Check if the user has already login **/
        if(message.getType() == MessageType.REGISTER_MESSAGE){
            routeMap.get(message.getType()).route(message);
        }
        System.out.println(message.getType().getType());
        long uid = SessionManager.getInstance().getUsrId(message.getFrom());
        Channel channel = SessionManager.getInstance().getSession(uid);
        if(channel == null){
            return;
        }
        if(!channel.isOpen()){
            return;
        }
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
