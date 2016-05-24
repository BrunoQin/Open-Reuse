package com.openreuse.server.request.logging;

import org.junit.Test;

import javax.xml.crypto.Data;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by ryw on 2016/5/24.
 */
public class LogWorkTest {
    @Test
    public void work() throws Exception {
        new LogWork(new Date()).work();
    }


}