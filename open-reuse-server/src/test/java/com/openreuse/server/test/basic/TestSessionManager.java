package com.openreuse.server.test.basic;

import com.openreuse.server.registry.validateRegistry.UserInfoDao;
import com.openreuse.server.request.session.SessionManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jasmine on 16/4/9.
 */
public class TestSessionManager {
    SessionManager sessionManager;
    @Before
    public void beforeTests(){
        sessionManager = SessionManager.getInstance();
    }

    @Test
    public void testGetUsrId(){
        long userId = sessionManager.getUsrId("Jasmine");
        System.out.print(userId);
    }

    @Test
    public void testRegisterUser(){
        long result = sessionManager.registerUsr("Testing","TestPW");
        UserInfoDao userInfoDao = new UserInfoDao();
        long id = userInfoDao.getIdbyName("Testing");
        if(result !=-1){
            assertEquals(id,result);
        }else
            System.out.print("Have registered");
    }

    @Test
    public void testValidUsrAndPass(){
        //Password Error
        boolean result = sessionManager.validUsrAndPass("Testing","Test");
        assertEquals(result,false);

        //Pass Validation
        result = sessionManager.validUsrAndPass("Testing","TestPW");
        assertEquals(result,true);
    }
}
