package com.openreuse.common.FileOperator;

import com.openreuse.common.message.Message;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Bruno on 16/4/23.
 */
public class FileUtil {

    private FileWriter fileWriter = null;
    private int part = 1;
    private int currentCap = 0;
    private String filePath;

    public FileUtil(String filePath){

        try{
            fileWriter = new FileWriter(filePath + "_" + part + ".txt");
            this.filePath = filePath;
            part++;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean writeFilePerLogin(Message message, int limitation){

        try{
            if (writeFileWithLimit(limitation)) {
                fileWriter.append(message.toString());
            } else {
                fileWriter = new FileWriter(filePath + "_" + part + ".txt");
                part++;
                fileWriter.append(message.toString());
            }
            return true;

        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean writeFileWithLimit(int limitation){
        if (currentCap <= limitation){
            currentCap++;
            return true;
        }
        currentCap = 0;
        return false;
    }

}
