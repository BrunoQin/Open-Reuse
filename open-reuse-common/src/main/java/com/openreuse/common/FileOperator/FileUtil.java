package com.openreuse.common.FileOperator;

import com.openreuse.common.message.Message;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Bruno on 16/4/23.
 */
public class FileUtil {

    private FileWriter fileWriter = null;

    public FileUtil(String filePath){

        try{
            fileWriter = new FileWriter(filePath);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean writeFilePerLogin(Message message){

        try{

            fileWriter.append(message.toString());
            return true;

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

}
