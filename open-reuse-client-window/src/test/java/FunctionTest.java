import com.openresure.client.config.ConfigManager;
import com.openreuse.window.body.LoginWindow;
import com.openreuse.window.body.SendMessageWindow;
import org.junit.Test;

/**
 * Created by Jasmine on 16/4/9.
 */
public class FunctionTest {


    @Test
    public void testOneClient(){
        for(int i=0;i<4;i++){
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.userNameField.setText("Jasmine");
            loginWindow.passwordField.setText("1234");
            loginWindow.serverField.setText("127.0.0.1");
            loginWindow.loginButton.doClick();
        }

    }

    @Test
    public void testSendMessage(){
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.userNameField.setText("Bruno");
        loginWindow.passwordField.setText("qinbo");
        loginWindow.serverField.setText("127.0.0.1");
        loginWindow.loginButton.doClick();
        for(int i=0;i<105;i++){
            loginWindow.sendMessageWindow.textField.setText("the "+i+" times send");
            loginWindow.sendMessageWindow.btn_send.doClick();

        }
    }

    @Test
    public void testDisconnect(){
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.userNameField.setText("Bruno");
        loginWindow.passwordField.setText("qinbo");
        loginWindow.serverField.setText("127.0.0.1");
        loginWindow.loginButton.doClick();
        SendMessageWindow sendMessageWindow = new SendMessageWindow("Bruno","127.0.0.1");
        sendMessageWindow.btn_stop.doClick();
        ConfigManager configManager = ConfigManager.getInstance();
        System.out.print(configManager.getLoginStatus("Bruno"));
    }

}
