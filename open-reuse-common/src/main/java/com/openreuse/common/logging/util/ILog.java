package com.openreuse.common.logging.util;

/**
 * Created by kimmin on 5/22/16.
 */
public interface ILog {
    void info(String log);
    void debug(String log);
    void warn(String log);
    void error(String log);
    void fatal(String log);
}
