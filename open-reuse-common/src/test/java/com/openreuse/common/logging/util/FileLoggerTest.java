package com.openreuse.common.logging.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ryw on 2016/5/24.
 */
public class FileLoggerTest {

    @Test
    public void testAll() throws Exception {
        FileLogger fl=  new FileLogger();
        fl.info("Info                ***************************************************");
        fl.debug("Debug               *****************************************************");
        fl.error("Error");
        fl.fatal("Fatal");
        fl.warn("Warning");
    }

}