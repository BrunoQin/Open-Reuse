package com.openreuse.common.FileOperator;

/**
 * Created by Jasmine on 16/4/24.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//=====================Zip Dictionary=========================

public class ZipFile {

/*

* inputFileName input dictionary
* zipFileName output dictionary

*/

    public static void zip(String inputFileName) throws Exception {

        Date today = new Date();

        createDir("Messagezip");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        String zipFileName = "./Messagezip/"+dateFormat.format(today)+".zip"; // 打包后文件名字
        System.out.print(zipFileName);
        zip(zipFileName, new File(inputFileName));

    }

    private static void zip(String zipFileName, File inputFile) throws Exception {

        File outfile = new File(zipFileName);
        outfile.createNewFile();
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(

                zipFileName));

        zip(out, inputFile, "");

        out.close();

    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {

        if (f.isDirectory()) {

            File[] fl = f.listFiles();

            out.putNextEntry(new ZipEntry(base + "/"));

            base = base.length() == 0 ? "" : base + "/";

            for (int i = 0; i < fl.length; i++) {

                zip(out, fl[i], base + fl[i].getName());

            }

        } else {

            out.putNextEntry(new ZipEntry(base));

            FileInputStream in = new FileInputStream(f);

            int b;

//System.out.println(base);

            while ((b = in.read()) != -1) {

                out.write(b);

            }

            in.close();

        }

    }

    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("the dictionary already exists");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("successful");
            return true;
        } else {
            System.out.println("failed");
            return false;
        }
    }

    public static void showTimer(int hour, int minute, int second, final String inputFileName) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    zip(inputFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //Set operation time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);//everyday
        calendar.set(year, month, day, hour,minute,second);
        Date date = calendar.getTime();
        Timer timer = new Timer();
        System.out.println(date);

        //task execute once per day
        timer.schedule(task, date);
    }


    public static void main(String [] temp){

        //zip file without timer
//        try {
//
//            zip("./message");//The directory you want to zip.
//
//        }catch (Exception ex) {
//
//            ex.printStackTrace();
//
//        }

        //zip file with timer
//        showTimer(0,00,00,"./message");//Zip file "./message" with the specific time 0:00:00

    }

}


