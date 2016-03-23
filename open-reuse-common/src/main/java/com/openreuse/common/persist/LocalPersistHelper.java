package com.openreuse.common.persist;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by kimmin on 3/23/16.
 */

public class LocalPersistHelper {

    /** I'm not quite sure if it's thread-safe or not **/
    private static ObjectMapper objectMapper = new ObjectMapper();

    /** Maping a util-map to json-style string **/
    /** Java BIO-style implementation **/
    public synchronized static String persistMapToLocal(Map map, File file){
        try{
            String json = objectMapper.writeValueAsString(map);
            return json;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    /** Map a json-style file to a map **/
    public synchronized static Map loadLocalToMap(File file){
        try{
            Map map = objectMapper.readValue(file, Map.class);
            return map;
        }catch (Throwable e){
            e.printStackTrace();
            return null;
        }
    }

}
