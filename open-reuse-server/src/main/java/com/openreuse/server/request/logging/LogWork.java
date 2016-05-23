package com.openreuse.server.request.logging;

import com.openreuse.common.FileOperator.ZipFile;
import com.openreuse.common.logging.util.LogEngine;
import com.openreuse.common.logging.util.LogType;
import com.openreuse.common.message.Message;
import com.openreuse.server.misc.Constants;
import com.openreuse.server.misc.worker.Worker;
import com.openreuse.server.request.dispatcher.RouteDispatcher;
import com.openreuse.server.request.file.PersistFileWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Bruno on 16/5/23.
 */
public class LogWork implements Worker {

    private Date register;
    private ZipFile zipFile;

    private static Logger logger = LoggerFactory.getLogger(LogWork.class);

    public LogWork(Date date){
        this.register = date;
        logger.info("A RAW JSON BYTES WORKER NEWED!");
    }

    public void work(){
        try{

            ZipFile.showTimer(0,00,00,"./logging");

            Date current = new Date();
            if (PersistFileWork.hourBetween(register, current) > Constants.FILE_OUTPUT_INTERVAL){

                Message message = RouteDispatcher.undumpMessageFromQueue();
                while(message != null){
                    LogEngine.generateLog(LogType.DEBUG, message.toString());
                    message = RouteDispatcher.undumpMessageFromQueue();
                }

                this.register = new Date();

            }

        }catch (Exception ie){
            ie.printStackTrace();
        }
    }

    public void run(){
        this.work();
    }

}
