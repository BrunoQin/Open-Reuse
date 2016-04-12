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

    public static void resetBasicCoef(){
        setCoef("max.msg_per_login", 100);
        setCoef("max_json_size", 1024);
        setCoef("server_port", 30000);
        setCoef("dump_stats_interval_millis", 1000 * 60);
    }

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
}
