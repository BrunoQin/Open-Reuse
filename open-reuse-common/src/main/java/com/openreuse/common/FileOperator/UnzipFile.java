package com.openreuse.common.FileOperator;


import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
/**
 * Created by Jasmine on 16/4/27.
 */
public class UnzipFile {

    /**
     * Unzip to Specified Path
     * @param zipPath
     * @param descDir
     */
    public static void unZipFiles(String zipPath,String descDir)throws IOException {
        unZipFiles(new File(zipPath), descDir);
    }

    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile,String descDir)throws IOException{
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        java.util.zip.ZipFile zip = new java.util.zip.ZipFile(zipFile);
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir+zipEntryName).replaceAll("\\*", "/");

            //Check if the path exists,or create new path
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if(!file.exists()){
                file.mkdirs();
            }

            //Check if the path is directory
            if(new File(outPath).isDirectory()){
                continue;
            }
            // Output file's path info
            System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while((len=in.read(buf1))>0){
                out.write(buf1,0,len);
            }
            in.close();
            out.close();
        }
        System.out.println("******************Unzip Completed********************");
    }

//    @Test
//    public void testUnzipFiles() throws IOException {
//        //unZipFiles
//        String zipPath = "./Messagezip/2016_04_27.zip";
//        String path = "../UnzipFiles/";
//        unZipFiles(zipPath, path);
//    }

}