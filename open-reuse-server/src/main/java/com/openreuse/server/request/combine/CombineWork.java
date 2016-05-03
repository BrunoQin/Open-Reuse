package com.openreuse.server.request.combine;

import com.openreuse.common.FileOperator.UnzipFile;
import com.openreuse.common.FileOperator.ZipFile;
import com.openreuse.server.misc.worker.Worker;
import com.openreuse.server.request.file.PersistFileWork;
import com.openreuse.server.request.json.RawBytesWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bruno on 16/5/3.
 */
public class CombineWork implements Worker {

    private Date register;
    private Date current;

    private static Logger logger = LoggerFactory.getLogger(RawBytesWorker.class);

    public CombineWork(Date date){
        this.register = date;
        logger.info("A RAW JSON BYTES WORKER NEWED!");
    }

    public void work(){

        try{

            current = new Date();
            if(PersistFileWork.hourBetween(register, current) >= 7){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                Date tem=new Date();
                String filePath=sdf.format(tem);

                for (int i = 0; i < 7; i++){
                    filePath = getSpecifiedDayBefore(filePath);
                    UnzipFile.unZipFiles("./Message/" + filePath + ".zip", "../UnzipFiles/");
                }

                ZipFile.zip("../UnzipFiles/");

                register = current;

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void run(){
        this.work();
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {

            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.setTime(date);
        int day = c.get(Calendar.DATE);

        c.set(Calendar.DATE, day - 1);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        return dayBefore;
    }

}
