package com.openreuse.common.persist;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jasmine on 16/4/8.
 */
public class testLocalPersistHelper {
    LocalPersistHelper localPersistHelper;
    @Before
    public void beforeTests(){
        localPersistHelper = new LocalPersistHelper();
    }

    @Test
    public void testPersistMapToLocal(){
        Map<String, Integer> statsMap = new ConcurrentHashMap();

        statsMap.put("loginSuccessCount", 10);
        statsMap.put("loginFailureCount", 10);
        statsMap.put("receivedCount", 10);
        statsMap.put("sendCount", 10);

        String result = localPersistHelper.persistMapToLocal(statsMap, new File("Testing.json"));
        System.out.print(result);
    }

    @Test
    public void testLoadLocalToMap(){

        Map<String, Integer> statsMap = localPersistHelper.loadLocalToMap(new File("/Users/caopeng/Desktop/test.json"));
        assertEquals("Error",statsMap.toString(),"{loginFailureCount=10, loginSuccessCount=10, receivedCount=10, sendCount=10}");
        //System.out.print(statsMap);
    }
}
