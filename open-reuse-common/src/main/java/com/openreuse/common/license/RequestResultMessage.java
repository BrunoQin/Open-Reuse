package com.openreuse.common.license;

/**
 * Created by kimmin on 4/12/16.
 */
public class RequestResultMessage {
    public RequestResultMessage(boolean success){
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success = false;

}
