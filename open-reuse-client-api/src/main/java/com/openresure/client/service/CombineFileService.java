package com.openresure.client.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bruno on 16/5/3.
 */
public class CombineFileService {

    public CombineFileService(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date=new Date();
        String filePath=sdf.format(date);
    }

    private static class Singleton{
        private static CombineFileService instance = new CombineFileService();
    }
    public static CombineFileService getInstance(){
        return Singleton.instance;
    }

    public void combineFile(){
        //Do something here.
    }

}
