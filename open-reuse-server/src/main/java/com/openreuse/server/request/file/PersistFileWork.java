package com.openreuse.server.request.file;

import com.openreuse.common.FileOperator.FileUtil;
import com.openreuse.common.FileOperator.ZipFile;
import com.openreuse.common.message.Message;
import com.openreuse.server.misc.Constants;
import com.openreuse.server.misc.worker.Worker;
import com.openreuse.server.request.dispatcher.RouteDispatcher;
import com.openreuse.server.request.json.RawBytesWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Bruno on 16/4/24.
 */
public class PersistFileWork implements Worker {

    private Date register;
    private FileUtil fileUtil;
    private ZipFile zipFile;

    private static Logger logger = LoggerFactory.getLogger(RawBytesWorker.class);

    public PersistFileWork(Date date){
        this.register = date;
        logger.info("A RAW JSON BYTES WORKER NEWED!");
    }

    public void work(){
        try{

            ZipFile.showTimer(0,00,00,"./message");

            Date current = new Date();
            if (hourBetween(register, current) > Constants.FILE_OUTPUT_INTERVAL){

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                Date date = new Date();
                String filePath = sdf.format(date);
                fileUtil = new FileUtil(filePath + ".txt");
                Message message = RouteDispatcher.undumpMessageFromQueue();
                while(message != null){
                    fileUtil.writeFilePerLogin(message, Constants.FILE_OUTPUT_LIMIT);
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

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int hourBetween(Date smdate,Date bdate) throws ParseException
    {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600);

        return Integer.parseInt(String.valueOf(between_days));

    }

}
