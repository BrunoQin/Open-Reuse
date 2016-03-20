package com.openreuse.server.test.validate;

import com.openreuse.server.pojo.notify.DelayedNotify;
import com.openreuse.server.registry.RegistryManager;
import com.openreuse.server.throttle.ThrottleManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Delayed;

/**
 * Created by kimmin on 3/20/16.
 */
public class SimpleValidateTest {
    @Mock
    private DelayedNotify mockNotify;
    private static final long MOCK_CLIENT_ID = 1L;


    @Before
    public void beforeTests(){
        MockitoAnnotations.initMocks(this);
//        mockNotify.setClientId(MOCK_CLIENT_ID);
//        mockNotify.setMilliDelay(1000);
        ThrottleManager.getInstance().startWorker();
    }

    @Test
    public void testRegistry(){
        assert RegistryManager.getInstance().checkLogin(MOCK_CLIENT_ID);
        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
        RegistryManager.getInstance().registerLogin(MOCK_CLIENT_ID);
        assert RegistryManager.getInstance().checkLogin(MOCK_CLIENT_ID);
    }

    @Test
    public void testRegistryExpire(){
        DelayedNotify notify = new DelayedNotify(MOCK_CLIENT_ID, 500);
        mockNotify.setClientId(MOCK_CLIENT_ID);
//        mockNotify.setMilliDelay(500);
        ThrottleManager.getInstance().notity(mockNotify);
        assert RegistryManager.getInstance().checkLogin(MOCK_CLIENT_ID);
    }

    @After
    public void afterTests(){
//        ThrottleManager.getInstance().stopWorker();
    }


}
