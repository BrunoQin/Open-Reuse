package com.openreuse.server.test.basic;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.throttle.ThrottleStatsManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Jasmine on 16/4/9.
 */
public class ThrottleTest {
    ThrottleStatsManager throttleStatsManager;
    @Before
    public void beforeTests(){
        throttleStatsManager = ThrottleStatsManager.getInstance();
    }

    @Test
    public void testIncMsgCount(){
        throttleStatsManager.incMsgCount(1);
        assertEquals(throttleStatsManager.getMsgCount(1),1);

        throttleStatsManager.incMsgCount(1);
        assertEquals(throttleStatsManager.getMsgCount(1),2);

    }

    @Test
    public void testCheckMsgCount(){
        long uid = 2;

        //Less than upper bound
        boolean result = throttleStatsManager.checkMsgCount(uid);
        assertEquals(result,true);

        //More than upper bound
        for(int i = 0;i< Constants.MAX_MSG_CNT_PER_LOGIN;i++) {
            throttleStatsManager.incMsgCount(uid);
        }
        result = throttleStatsManager.checkMsgCount(uid);
        assertEquals(result,false);

    }

    @Test
    public void testClrMsgCount(){
        long uid = 3;
        throttleStatsManager.clrMsgCount(uid);
        assertEquals(throttleStatsManager.getMsgCount(uid),0);
    }

    @Test
    public void testIncForwardedMsgCount(){

        throttleStatsManager.incForwardedMsgCount();
        System.out.print(throttleStatsManager.getForwardedMsgCount());
    }

    @Test
    public void testClrForwardedMsgCount(){
        throttleStatsManager.incForwardedMsgCount();
        throttleStatsManager.clrForwardedMsgCount();
        assertEquals(throttleStatsManager.getForwardedMsgCount(),0);
    }

   @Test
    public void testInvalidLoginCount(){
       throttleStatsManager.incInvalidLoginCount();
       assertEquals(throttleStatsManager.getInvalidLoginCount(),1);

       throttleStatsManager.clrInvalidLoginCount();
       assertEquals(throttleStatsManager.getInvalidLoginCount(),0);

   }

    @Test
    public void testValidLoginCount(){
        throttleStatsManager.incValidLoginCount();
        assertEquals(throttleStatsManager.getValidLoginCount(),1);

        throttleStatsManager.clrValidLoginCount();
        assertEquals(throttleStatsManager.getValidLoginCount(),0);

    }

    @Test
    public void testrReceivedMsgCount(){
        throttleStatsManager.incReceivedMsgCount();
        assertEquals(throttleStatsManager.getReceivedMsgCount(),1);

        throttleStatsManager.clrReceivedMsgCount();
        assertEquals(throttleStatsManager.getReceivedMsgCount(),0);

    }

    @Test
    public void testIgnoredMsgCount(){
        throttleStatsManager.incIgnoredMsgCount();
        assertEquals(throttleStatsManager.getIgnoredMsgCount(),1);

        throttleStatsManager.clrIgnoredMsgCount();
        assertEquals(throttleStatsManager.getIgnoredMsgCount(),0);
    }
}
