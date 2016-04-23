package com.openreuse.server.util;

import com.openreuse.common.message.Message;
import com.openreuse.server.misc.Constants;
import com.openreuse.server.request.dispatcher.RouteDispatcher;

import java.io.FileWriter;

/**
 * Created by Bruno on 16/4/23.
 */
public class FileUtil {

    private static FileWriter fileWriter = null;


    public static boolean writeFilePerDay(){

        try{

            fileWriter = new FileWriter(Constants.FILE_PATH);
            Message message = new Message();
            message = RouteDispatcher.undumpMessageFromQueue();
            while (message != null){
                fileWriter.write(message.toString());
                message = RouteDispatcher.undumpMessageFromQueue();
            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                fileWriter.close();

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        return false;

    }

}
