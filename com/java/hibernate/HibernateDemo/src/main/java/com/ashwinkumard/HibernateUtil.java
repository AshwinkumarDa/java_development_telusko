package com.ashwinkumard;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        String fullJdbcUrl = System.getenv("DB_PG_URL_TELUSKO");
        String dbUser = System.getenv("DB_PG_USER_TELUSKO");
        String dbPassword = System.getenv("DB_PG_PASSWORD_TELUSKO");

        // This code is deprecated
        //         SessionFactory factory = new Configuration()
        //                 .setProperty("hibernate.connection.url", fullJdbcUrl)
        //                 .setProperty("hibernate.connection.username", dbUser)
        //                 .setProperty("hibernate.connection.password", dbPassword)
        //                 .addAnnotatedClass(com.ashwinkumard.Alien.class)
        //                 .buildSessionFactory();
        //         return factory;


        return new HibernatePersistenceConfiguration("telusko-unit")
                .jdbcUrl(fullJdbcUrl)
                .jdbcUsername(dbUser)
                .jdbcPassword(dbPassword)
                .managedClass(com.ashwinkumard.Alien.class)
                .property("hibernate.show_sql", "true")
                .property("hibernate.hbm2ddl.auto", "update")
                .createEntityManagerFactory()
                .unwrap(SessionFactory.class);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

