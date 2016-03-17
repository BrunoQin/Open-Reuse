package com.OpenReuse.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Bruno on 16/3/17.
 */
public class MySqlHelper {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
        //第一步:读取Hibernate的配置文件  hibernamte.cfg.xml文件
        Configuration con=new Configuration().configure();
        //第三步:创建会话工厂
        SessionFactory sessionFactory=con.buildSessionFactory();
        return sessionFactory;
    }

    public static Session getSession()
    {
        return getSessionFactory().openSession();
    }

}
