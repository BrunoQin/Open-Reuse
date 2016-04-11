package com.openreuse.server.test.basic;

import com.openreuse.server.registry.RegistryManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jasmine on 16/4/9.
 */
public class RegisterManagerTest {
    RegistryManager registryManager;
    @Before
    public void BeforeTests(){
       registryManager = RegistryManager.getInstance();
    }

    @Test
    public void testRegisterLogin() {
        //With No Login
        System.out.print(registryManager.getLoginCountByID(1)+"\n");

        //Login 10 times
        for(int i = 0;i<10;i++){
            registryManager.registerLogin(1);
        }
        System.out.print(registryManager.getLoginCountByID(1));
    }
    @Test
    public void testCheckLogin() {
        boolean result;
        //Login less than three times
        for(int i=0;i<3;i++){
            registryManager.registerLogin(2);
        }
        result = registryManager.checkLogin(2);
        assertEquals(result,true);

          //Login more than three times
        registryManager.registerLogin(2);
        result = registryManager.checkLogin(2);
        assertEquals(result,false);
    }


    @Test
    public void testCountDownLogin() {
        long clinetID = 3;
        for(int i=0;i<2;i++){
            registryManager.registerLogin(clinetID);
        }
        registryManager.countDownLogin(clinetID);
        System.out.print(registryManager.getLoginCountByID(clinetID)+"\n");

        registryManager.countDownLogin(clinetID);
        System.out.print(registryManager.getLoginCountByID(clinetID));

    }


}
