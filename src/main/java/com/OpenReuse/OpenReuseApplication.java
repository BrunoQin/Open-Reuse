package com.OpenReuse;

import com.OpenReuse.model.UserEntity;
import com.OpenReuse.util.MySqlHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Created by Bruno on 16/3/17.
 */

@SpringBootApplication
public class OpenReuseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenReuseApplication.class, args);

        UserEntity user = new UserEntity();
        user.setUserId(3);
        user.setUserName("33");
        user.setPassword("333");

        Session s = null;
        Transaction t = null;

        try{
            s = MySqlHelper.getSession();
            t = s.beginTransaction();
            s.save(user);
            t.commit();
        }catch (Exception e)
        {
            if(t!=null)
            {
                t.rollback();
            }
        }
        finally
        {
            if(s!=null)
            {
                s.close();
            }
        }
    }
}
