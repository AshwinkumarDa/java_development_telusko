package com.ashwinkumard;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        // changeColumnName();
        read();
    }

    static void insert() {
        Alien a1 = new Alien();
        a1.setId(103);
        a1.setName("Kiran");
        a1.setTech("Gen AI");

//        Configuration cfg = new Configuration();
//        cfg.addAnnotatedClass(com.ashwinkumard.Alien.class);
//        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        session.persist(a1);

        transaction.commit();

        session.close();
        factory.close();
    }
    static void read() {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        // Eager Fetching
        // Prefer to use `session.find()` API over `session.get()` and `session.getById()`.
        Alien a2 = session.find(Alien.class, 104);
        System.out.println("Alien2: "+ a2);

        // Eager Fetching
        // Prefer to use `session.find()`
        Alien a1 = session.get(Alien.class, 101);
        System.out.println("Alien: "+ a1);

        Alien anotherA1 = session.get(Alien.class, 101);
        System.out.println("Another Alien: "+ anotherA1);



        // Eager Fetching
        Alien a3 = session.byId(Alien.class).load(103);
        System.out.println("Alien3: "+ a3);

        // Lazy Fetching
        Alien a4= session.byId(Alien.class).getReference(101); // lazy fetching
        // System.out.println(a4); // it will give exception if record  not found

        // TO check what is difference between all above session APIs


        session.close();
        factory.close();
    }
    static  void update() {

        Alien a1 = new Alien();
        a1.setId(104);
        a1.setName("Avni");
        a1.setTech("Database");

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();
        // Merge will insert new record if not found
        session.merge(a1);
        transaction.commit();

        session.close();
        factory.close();
    }

    static void delete() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        Alien a1 = session.find(Alien.class, 104);
        session.remove(a1);

        transaction.commit();

        session.close();
        factory.close();
    }

    static void changeColumnName() {
        // update Alien class
        Alien a1 = new Alien();
        a1.setId(104);
        a1.setName("Avni");
        a1.setTech("Database");

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        Transaction transaction = session.beginTransaction();

        session.persist(a1);

        transaction.commit();

        session.close();
        factory.close();
    }
}
