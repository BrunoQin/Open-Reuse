import com.openresure.client.config.ConfigManager;
import com.openresure.client.listener.ValidateLoginListener;
import com.openresure.client.listener.ValidateRegisterListener;
import org.junit.Test;

/**
 * Created by Jasmine on 16/4/7.
 */
public class ListenerTest {

    @Test
    public void testValidateLoginListener(){
        ValidateLoginListener validateLoginListener = new ValidateLoginListener();
        validateLoginListener.onMessageArrive("Jasmine");
        System.out.print(ConfigManager.getInstance().getLoginStatus("Jasmine"));
    }

    @Test
    public void testValidateRegisterListener(){


        ValidateRegisterListener validateRegisterListener = new ValidateRegisterListener();
        validateRegisterListener.onMessageArrive("Jasmine");
        System.out.print(ConfigManager.getInstance().registerSuccess+"\n");
        ConfigManager.getInstance().registername = "Jasmine";
        System.out.print(ConfigManager.getInstance().registerSuccess);

    }
}
