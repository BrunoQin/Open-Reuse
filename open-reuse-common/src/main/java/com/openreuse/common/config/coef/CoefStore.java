package com.openreuse.common.config.coef;

import com.openreuse.common.config.coef.listener.CoefListener;
import com.openreuse.common.config.coef.type.CoefType;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kimmin on 4/11/16.
 */
public class CoefStore {

    private static Map<String, AbstractCoef> coefMap = new ConcurrentHashMap<>();

    private static Map<String, List<CoefListener>> listenerMap
            = new ConcurrentHashMap<String, List<CoefListener>>();


    private static ObjectMapper om = new ObjectMapper();

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

    public static void dumpConfigToFile(File jsonFile){
        Map map = new HashMap();
        for(String key: coefMap.keySet()){
            AbstractCoef ac = coefMap.get(key);
            if(ac.getType().equals(CoefType.INTEGER)){
                Map vmap = new HashMap();
                vmap.put("value", (Integer) ac.getValue());
                vmap.put("type", CoefType.INTEGER);
                map.put(key, vmap);
            }else if(ac.getType().equals(CoefType.VARCHAR)){
                Map vmap = new HashMap();
                vmap.put("value", (String) ac.getValue());
                vmap.put("type", CoefType.VARCHAR);
                map.put(key, vmap);
            }else{
                /** Temp do nothing .. **/
            }
        }
        try{
            om.writeValue(jsonFile, map);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public static void loadConfigToFile(File jsonFile){
        coefMap.clear();
        try{
            Map map = om.readValue(jsonFile, Map.class);
            for(Object key: map.keySet()){
                Map vmap = (Map) map.get(key);
                CoefType type =  CoefType.getTypeViaString((String) vmap.get("type"));
                if(type.equals(CoefType.INTEGER)){
                    setCoef((String) key, (Integer) vmap.get("value"));
                }else if(type.equals(CoefType.VARCHAR)){
                    setCoef((String) key, (String) vmap.get("value"));
                }else{}
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
