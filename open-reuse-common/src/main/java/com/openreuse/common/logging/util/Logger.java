package com.openreuse.common.logging.util;

/**
 * Created by kimmin on 5/22/16.
 */
public abstract class Logger implements ILog{
    public void info(String log){
        System.out.println("[UNDIRECTED]:" + log);
    }
    public void debug(String log){
        System.out.println("[UNDIRECTED]:" + log);
    }
    public void warn(String log){
        System.out.println("[UNDIRECTED]:" + log);
    }
    public void error(String log){
        System.out.println("[UNDIRECTED]:" + log);
    }
    public void fatal(String log){
        System.out.println("[UNDIRECTED]:" + log);
    }
}
