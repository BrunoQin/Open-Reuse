package com.openresure.client.service;

import com.openreuse.common.FileOperator.FileUtil;
import com.openreuse.common.message.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bruno on 16/4/24.
 */
public class PersistFileService {

    private FileUtil fileUtil;

    public PersistFileService(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date=new Date();
        String filePath=sdf.format(date);
        fileUtil = new FileUtil(filePath + ".txt");
    }

    private static class Singleton{
        private static PersistFileService instance = new PersistFileService();
    }
    public static PersistFileService getInstance(){
        return Singleton.instance;
    }

    public void writeFile(Message message){
        fileUtil.writeFilePerLogin(message);
    }

}
