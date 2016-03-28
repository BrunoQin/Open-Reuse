package com.openreuse.server.test.validate;

import com.openreuse.server.registry.validateRegistry.UserInfoDao;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Jasmine on 16/3/26.
 */
public class UserInfoDaoTest {

    @Test
    public void TestAddUser() throws Exception {
        UserInfoDao userInfoDao = new UserInfoDao();
        boolean result = userInfoDao.addUser("Jasmine","1234");
        System.out.print(result);

    }

    @Test
    public void TestGetIdbyName() throws Exception{
        UserInfoDao userInfoDao = new UserInfoDao();
        long id = userInfoDao.getIdbyName("Judy");
        System.out.print(id);
    }

    @Test
    public void TestValidatePassword()throws Exception{
        UserInfoDao userInfoDao = new UserInfoDao();
        boolean passValidation = userInfoDao.validatePassword("Jasmine","1234");
        assertEquals(true,passValidation);
    }
}