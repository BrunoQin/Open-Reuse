package com.openreuse.common.FileOperator;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.junit.Test;

import java.io.File;

import java.util.zip.ZipException;

/**
 * Created by Jasmine on 16/5/4.
 */
public class ZipWithPassword {

    /**
     * Use password to unzip file to specified directory
     * <p>
     * If directory doesn't exist, create it, if it's wrong, throw exception
     * @param zip Path of File to zip
     * @param dest Unzip path
     * @param passwd Password
     */
    public static void unzipwithPw(String zip, String dest, String passwd) throws ZipException, net.lingala.zip4j.exception.ZipException {
        File zipFile = new File(zip);
        unzipwithPw(zipFile, dest, passwd);
    }


    public static void unzipwithPw(File zipFile, String dest, String passwd) throws ZipException, net.lingala.zip4j.exception.ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("GBK");
        if (!zFile.isValidZipFile()) {
            throw new ZipException("File to zip may be broken.");
        }
        File destDir = new File(dest);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        if (zFile.isEncrypted()) {
            zFile.setPassword(passwd.toCharArray());
        }
        zFile.extractAll(dest);

    }




    /**
     * Zip file with password to the specified path.
     * @param src path of file to zip
     * @param dest specified path of .zip
     * @param passwd password
     * @return zip file path, null means zip failed.
     */
    public static String zipwithPw(String src, String dest,  String passwd) {
        File srcFile = new File(src);
        com.openreuse.common.FileOperator.ZipFile.createDir(dest);
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);           // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    // 压缩级别
        if(!passwd.equals("")) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(passwd.toCharArray());
        }
        try {
            ZipFile zipFile = new ZipFile(dest);
            if (srcFile.isDirectory()) {
                zipFile.addFolder(srcFile, parameters);
            } else {
                zipFile.addFile(srcFile, parameters);
            }
            return dest;
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create file
     *
     * @param srcFile source File
     * @param destParam Zip file Path
     * @return exact path of zipfile
     */
    private static String buildDestinationZipFilePath(File srcFile,String destParam) {
        if (destParam.isEmpty()) {
            if (srcFile.isDirectory()) {
                destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
            } else {
                String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                destParam = srcFile.getParent() + File.separator + fileName + ".zip";
            }
        } else {
            createDestDirectoryIfNecessary(destParam);  // 在指定路径不存在的情况下将其创建出来
            if (destParam.endsWith(File.separator)) {
                String fileName = "";
                if (srcFile.isDirectory()) {
                    fileName = srcFile.getName();
                } else {
                    fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
                }
                destParam += fileName + ".zip";
            }
        }
        return destParam;
    }

    private static void createDestDirectoryIfNecessary(String destParam) {
        File destDir = null;
        if (destParam.endsWith(File.separator)) {
            destDir = new File(destParam);
        } else {
            destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
        }
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    @Test
    public void testZipWithPassword(){
        zipwithPw("/Users/caopeng/Desktop/test","/Users/caopeng/Desktop","1234");
    }

    @Test
    public void testUnzipWithPassword() throws ZipException, net.lingala.zip4j.exception.ZipException {
        zipwithPw("/Users/caopeng/Desktop/test","/Users/caopeng/Desktop","1234");
        unzipwithPw("/Users/caopeng/Desktop/test.zip","/Users/caopeng/Desktop/test","1234");
    }
}
