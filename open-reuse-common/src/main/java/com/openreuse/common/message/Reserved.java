package com.openreuse.common.message;

/**
 * Created by kimmin on 3/23/16.
 */
public class Reserved {

    public Reserved(){}

    public Reserved(String content){
        this.content = content;
    }

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
