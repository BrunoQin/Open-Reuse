package com.openreuse.server.request.combine;

import com.openreuse.common.FileOperator.UnzipFile;
import com.openreuse.common.FileOperator.ZipWithPassword;
import com.openreuse.server.misc.Constants;
import com.openreuse.server.misc.worker.Worker;
import com.openreuse.server.request.file.PersistFileWork;
import com.openreuse.server.request.json.RawBytesWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
            if(PersistFileWork.hourBetween(register, current) >= 7 * 24){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                Date tem=new Date();
                String filePath=sdf.format(tem);

                for (int i = 0; i < 7; i++){
                    filePath = getSpecifiedDayBefore(filePath);
                    UnzipFile.unZipFiles("./Message/" + filePath + ".zip", "../UnzipFiles/");
                }

                File[] fs = showAllFiles(new File("../UnzipFiles/"));
                int num = Constants.COMBINE_FILE_OUTPUT_LIMIT / Constants.FILE_OUTPUT_LIMIT;
                int count = 0;
                File combine = new File("../UnzipFiles/combine_" + count);
                for (int i = 1; i < fs.length + 1; i++){
                    if (i % num != 0){
                        combine = new File(fs[i], "../UnzipFiles/combine_" + count);
                    } else {
                        try{
                            ZipWithPassword.zipwithPw("../UnzipFiles/combine_" + count, "../UnzipFiles/", Constants.COMBINE_PSW);
                            count++;
                            combine = new File("../UnzipFiles/combine_" + count);
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

                //ZipFile.zip("../UnzipFiles/");

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

    /**
     * 获取当前文件夹下所有的文件
     *
     * @param dir
     * @return
     * @throws Exception
     */
    final static File[] showAllFiles(File dir) throws Exception{
        File[] fs = dir.listFiles();
        return fs;
    }

}
