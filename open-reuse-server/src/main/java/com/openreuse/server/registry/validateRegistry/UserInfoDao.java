package com.openreuse.server.registry.validateRegistry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;


/**
 * Created by Jasmine on 16/3/26.
 */
public class UserInfoDao {

    private Session session = null;

    UserInfoDao(){
        Configuration configuration = new Configuration();
        SessionFactory factory = configuration.configure().buildSessionFactory();
        session = factory.openSession();
    }

    //Register with username & password
    public boolean addUser(String username, String password){
        if (existOrNot(username,password)){
            //User already exists, return false
            return false;
        }else{
            //Insert the new record
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUsername(username);
            userInfoEntity.setPassword(password);
            userInsert(userInfoEntity);
            return true;
        }
    }

    //Insert record to database.
    private void userInsert(UserInfoEntity userInfoEntity){
        Transaction transaction = session.beginTransaction();
        session.save(userInfoEntity);
        transaction.commit();
    }

    //Check if the user already exists.
    private boolean existOrNot(String username, String password){
        Query query = session.createQuery("from UserInfoEntity where username = username and password = password");
        List list = query.list();
        if(list != null && !list.isEmpty()) {
            return true;
        }else
            return false;
    }


}

