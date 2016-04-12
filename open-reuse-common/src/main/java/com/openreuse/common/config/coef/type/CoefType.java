package com.openreuse.common.config.coef.type;

/**
 * Created by kimmin on 4/11/16.
 */
public enum CoefType {

    VARCHAR("varchar", 0),
    INTEGER("integer", 1),
    LONG("long", 2),
    BYTE("byte", 3);


    CoefType(String name, Integer index){
        this.index = index;
        this.name = name;
    }

    private String name;
    private Integer index;

    public static String getName(int index){
        for(CoefType type: CoefType.values()){
            if(type.index == index){
                return type.name;
            }
        }
        return null;
    }

}
