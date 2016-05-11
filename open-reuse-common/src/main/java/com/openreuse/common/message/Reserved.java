package com.openreuse.common.message;

import java.io.Serializable;

/**
 * Created by kimmin on 3/23/16.
 */
public class Reserved implements Serializable{

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
