package com.openreuse.common.monitor;

/**
 * Created by kimmin on 4/12/16.
 */
public enum Status {
    GREEN(0),
    YELLOW(1),
    RED(2);

    private int status;

    Status(int status){
        this.status = status;
    }

}
