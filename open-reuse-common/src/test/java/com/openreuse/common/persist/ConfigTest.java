package com.openreuse.common.persist;

import com.openreuse.common.config.ConfigUtil;
import com.openreuse.common.config.coef.AbstractCoef;
import com.openreuse.common.config.coef.type.CoefType;
import org.junit.Test;

import java.io.File;

/**
 * Created by kimmin on 4/19/16.
 */
public class ConfigTest {
    @Test
    public void doTest(){
        ConfigUtil.setBasicCoef("1", "2");
        AbstractCoef ac = ConfigUtil.getBasicCoef("1");
        if(ac.getType().equals(CoefType.VARCHAR)){
            System.out.println((String) ac.getValue());
        }
    }
    @Test
    public void doTest2(){
        ConfigUtil.setBasicCoef("1" , 2);
        AbstractCoef ac = ConfigUtil.getBasicCoef("1");
        if(ac.getType().equals(CoefType.INTEGER)){
            System.out.println((Integer) ac.getValue());
        }
    }

    @Test
    public void doTest3(){
        ConfigUtil.setBasicCoef("1", "2");
        File jsonFile = new File("config.json");
        ConfigUtil.dumpBasicCoef(jsonFile);
    }
    @Test
    public void doTest4(){
        File jsonFile = new File("config.json");
        ConfigUtil.loadBasicCoef(jsonFile);
        ConfigUtil.setBasicCoef("1", "2");
        AbstractCoef ac = ConfigUtil.getBasicCoef("1");
        if(ac.getType().equals(CoefType.VARCHAR)){
            System.out.println((String) ac.getValue());
        }
    }
}
