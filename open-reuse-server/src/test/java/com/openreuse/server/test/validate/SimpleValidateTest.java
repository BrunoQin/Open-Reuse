package com.openreuse.server.test.validate;

import com.openreuse.server.pojo.notify.DelayedNotify;
import com.openreuse.server.registry.RegistryManager;
import com.openreuse.server.throttle.ThrottleManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by kimmin on 3/20/16.
 */
public class SimpleValidateTest {
    @Mock
    private DelayedNotify mockNotify;
    private static final long MOCK_CLIENT_ID = 1L;
    private static final int MOCK_CLIENT_NUMBER = 20;
    private  static final int MOCK_THREAD_NUMBER = 10;
    private static final int CHECK_LOGIN_NUMBER = 3; //Login will be successful less than 3 times.
    private static final int CHECK_EXPIRE_NUMBER = 5;
    @Before
    public void beforeTests(){
        MockitoAnnotations.initMocks(this);
        ThrottleManager.getInstance().startWorker();

    }

    @Test
    public void testRegistry(){

        for (int index =0; index<MOCK_CLIENT_NUMBER;index++){
            long Number = index;//Client ID
            assert RegistryManager.getInstance().checkLogin(Number);
            for(int loginIndex = 0; loginIndex <CHECK_LOGIN_NUMBER; loginIndex++) {
                RegistryManager.getInstance().registerLogin(Number);
            }

            System.out.print("Client:"+(Number+1)+"\t"+RegistryManager.getInstance().checkLogin(Number)+"\n");
            assert  RegistryManager.getInstance().checkLogin(Number);
        }

    }

    @Test
    public void testRegistryExpire(){
        //Multiple Clients & Multiple Threads
        Thread[] threads = new Thread[MOCK_THREAD_NUMBER];
        for (int index =0; index<MOCK_CLIENT_NUMBER;index++) {
            final long Number = index;//Client ID
            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    for(int loginIndex = 0; loginIndex <CHECK_EXPIRE_NUMBER; loginIndex++) {
                        RegistryManager.getInstance().registerLogin(Number);
                        System.out.print("Client:"+(Number+1)+"\t"+RegistryManager.getInstance().checkLogin(Number)+"\n");
                        //false: login failed
                        //true: login success
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    assert RegistryManager.getInstance().checkLogin(Number);
                    //System.out.print("No."+Number+"\t"+RegistryManager.getInstance().checkLogin(Number)+"\n");
                }
            };
            for(int i = 0; i<MOCK_THREAD_NUMBER; i++){
                threads[i] = new Thread(runnable);
                threads[i].start();
            }

        }

//        mockNotify.setClientId(MOCK_CLIENT_ID);
//        mockNotify.setMilliDelay(500);
//        RegistryManager.getInstance()

//        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
//        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
//        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
//        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
//        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
//        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
//
//        ThrottleManager.getInstance().notity(notify);
//        ThrottleManager.getInstance().notity(notify);
//
//
//        try{
//            Thread.currentThread().sleep(5000);
//        }catch (InterruptedException ie){
//            ie.printStackTrace();
//        }
//
//        assert RegistryManager.getInstance().checkLogin(MOCK_CLIENT_ID);

    }

    @After
    public void afterTests(){
        ThrottleManager.getInstance().stopWorker();
    }


}