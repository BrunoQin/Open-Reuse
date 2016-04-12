package com.openreuse.common.config.coef;

import com.openreuse.common.config.coef.listener.CoefListener;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 4/11/16.
 */
public class CoefStore {

    private static Map<String, AbstractCoef> coefMap = new ConcurrentHashMap<>();

    private static Map<String, List<CoefListener>> listenerMap
            = new ConcurrentHashMap<String, List<CoefListener>>();



    public static void setCoef(String name, String coef){
        coefMap.put(name, new StringCoef(coef));
    }

    public static void setCoef(String name, Integer coef){
        coefMap.put(name, new IntegerCoef(coef));
    }

    private static void publishModication(String name, AbstractCoef coef) {
        Collection<String> keySet = Collections.unmodifiableCollection(listenerMap.keySet());
        for(String coefName: keySet){
            if(coefName.equals(name)){
                List<CoefListener> list = listenerMap.get(name);
                Iterator<CoefListener> iter = list.iterator();
                while(iter.hasNext()){
                    CoefListener listener = iter.next();
                    listener.onCoefChanged(coef);
                }
            }
        }
    }

    public static AbstractCoef getCoef(String name){
        return coefMap.get(name);
    }

    public static void registerListener(String name, CoefListener listener){
        if(listenerMap.containsKey(name)){
            List<CoefListener> list = listenerMap.get(name);
            list.add(listener);
        }else{
            List<CoefListener> list = new LinkedList<>();
            list.add(listener);
            listenerMap.put(name, list);
        }
    }

    public static void removeAllListener(String name){
        listenerMap.remove(name);
    }

}
