import org.junit.Test;

import static com.openreuse.common.FileOperator.UnzipFile.unZipFiles;
import static com.openreuse.common.FileOperator.ZipFile.zip;

/**
 * Created by Jasmine on 16/4/27.
 */
public class testZipFile {

    @Test
    public void testUnzipFiles() throws Exception {

        //ZipFiles
        zip("../documents");

        //unZipFiles
        String zipPath = "./Messagezip/2016_04_27.zip";
        String path = "../UnzipFiles/";
        unZipFiles(zipPath, path);
    }
}
