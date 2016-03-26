package com.openreuse.server.registry.validateRegistry;

import org.junit.Test;

/**
 * Created by Jasmine on 16/3/26.
 */
public class UserInfoDaoTest {

    @Test
    public void TestAddUser() throws Exception {
        UserInfoDao userInfoDao = new UserInfoDao();
        boolean fail = userInfoDao.addUser("Julia","1234");
        System.out.print("second time insert:" + fail);

    }

    @Test
    public void TestExist() throws Exception{

    }
}