package com.openreuse.common.config.coef.type;

/**
 * Created by kimmin on 4/11/16.
 */
public enum CoefType {

    VARCHAR("varchar"),
    INTEGER("integer"),
    LONG("long"),
    BYTE("byte");


    CoefType(String name){
        this.name = name;
    }

    private String name;

    public static CoefType getTypeViaString(String typeStr){
        if(typeStr.equals("VARCHAR")) return CoefType.VARCHAR;
        if(typeStr.equals("INTEGER")) return CoefType.INTEGER;
        return null;
    }

//    public static String getName(int index){
//        for(CoefType type: CoefType.values()){
//            if(type.index == index){
//                return type.name;
//            }
//        }
//        return null;
//    }

}
