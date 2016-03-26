package com.openreuse.server.registry.validateRegistry;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;


/**
 * Created by Jasmine on 16/3/26.
 */
public class UserInfoDao {

    private Session session = null;

    public UserInfoDao(){
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
    public boolean existOrNot(String username, String password){
        //Query query = session.createQuery("from UserInfoEntity where username = username and password = password");
        //List list = query.list();
        Criteria criteria = session.createCriteria(UserInfoEntity.class);
        Criterion criterion = Restrictions.like("username",username);
        criteria.add(criterion);
        List<UserInfoEntity> list = criteria.list();
        if(list != null && !list.isEmpty()) {
            return true;
        }else
            return false;
    }

    //Get userid by username
    public long getIdbyName(String username){
        long id = -1;
        Criteria criteria = session.createCriteria(UserInfoEntity.class);
        Criterion criterion = Restrictions.like("username",username);
        criteria.add(criterion);
        List<UserInfoEntity> list = criteria.list();
        if(list != null && !list.isEmpty()) {
           id = list.get(0).getUserid();
        }
        return id;
    }


}

