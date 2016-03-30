package com.openresure.client.service;

import com.openresure.client.listener.MessageListener;
import com.openreuse.common.message.Message;
import com.openreuse.common.message.MessageType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by min.jin on 2016/3/28.
 */
public class MessageNotifyService {
    private MessageNotifyService(){}
    private static class Singleton{
        private static MessageNotifyService instance = new MessageNotifyService();
    }
    public static MessageNotifyService getInstance(){
        return Singleton.instance;
    }

    private Map<MessageType, List<MessageListener>> map = new ConcurrentHashMap<MessageType, List<MessageListener>>();

    public void registerListener(MessageType type, MessageListener listener){
        if(map.containsKey(type)){
            List<MessageListener> list = map.get(type);
            list.add(listener);
            /** Maybe useless? **/
            map.put(type, list);
        }else{
            List<MessageListener> list = new LinkedList<MessageListener>();
            list.add(listener);
            map.put(type, list);
        }
    }

    public void publish(Message message){
        MessageType type = message.getType();
        if(map.containsKey(type)){
            List<MessageListener> list = map.get(type);
            Collection<MessageListener> listeners = Collections.unmodifiableCollection(list);
            Iterator<MessageListener> iter = listeners.iterator();
            while(iter.hasNext()){
                MessageListener listener = iter.next();
                if(listener.isValid()){
                    listener.onMessageArrive(message.getBody());
                }
            }
        }
    }
}
