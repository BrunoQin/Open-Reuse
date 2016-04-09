package com.openreuse.server.test.basic;

import com.openreuse.server.misc.Constants;
import com.openreuse.server.pojo.notify.DelayedNotify;
import com.openreuse.server.validate.Validators;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jasmine on 16/4/9.
 */
public class ValidatorsTest {

    DelayedNotify delayedNotify;
    Validators validators;

    @Before
    public void beforeTests(){
        validators = new Validators();
    }

    @Test
    public void testValidateNotify(){
        delayedNotify = new DelayedNotify(1, Constants.INVALID_DELAY_VALUE);
        boolean result = validators.validateNotify(delayedNotify);
        assertEquals(result,false);

        delayedNotify = new DelayedNotify(1,100);
        result = validators.validateNotify(delayedNotify);
        assertEquals(result,true);
    }

    @Test
    public void testValidateClientID(){
        boolean result = validators.validateClientID(1);
        assertEquals(result,true);

        result = validators.validateClientID(Constants.INVALID_CLIENT_ID);
        assertEquals(result,false);
    }
}
