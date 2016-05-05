import com.openreuse.common.FileOperator.ZipWithPassword;
import net.lingala.zip4j.exception.ZipException;
import org.junit.Test;

/**
 * Created by Jasmine on 16/5/4.
 */
public class testZipFileWithPw {
    @Test
    public void testZipWithPassword(){
        ZipWithPassword zipWithPassword = new ZipWithPassword();
        zipWithPassword.zipwithPw("/Users/caopeng/Desktop/test","/Users/caopeng/Desktop/test.zip","1234");
    }

    @Test
    public void testUnzipWithPassword() throws ZipException, java.util.zip.ZipException {
        ZipWithPassword zipWithPassword = new ZipWithPassword();
        zipWithPassword.zipwithPw("/Users/caopeng/Desktop/test","/Users/caopeng/Desktop/test.zip","1234");
        zipWithPassword.unzipwithPw("/Users/caopeng/Desktop/test.zip","/Users/caopeng/Desktop/test","1234");
    }
}

