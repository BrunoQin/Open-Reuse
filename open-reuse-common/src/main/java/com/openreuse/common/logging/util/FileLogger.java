package com.openreuse.common.logging.util;

import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kimmin on 5/22/16.
 */
public class FileLogger extends Logger{

    private PrintWriter writer = null;

    public FileLogger(){
        try{
            writer = new PrintWriter(new FileOutputStream(new File("tmp.log")));
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void info(String info){
        writer.println(info);
    }

    @Override
    public void debug(String debug){
        writer.println(debug);
    }

    @Override
    public void warn(String warn){
        writer.println(warn);
    }

    @Override
    public void error(String error){
        writer.println(error);
    }

    @Override
    public void fatal(String fatal){
        writer.println(fatal);
    }
}
