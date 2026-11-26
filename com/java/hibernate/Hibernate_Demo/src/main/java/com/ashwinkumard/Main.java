package com.ashwinkumard;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import static jakarta.persistence.Persistence.createEntityManagerFactory;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Alien a1 = new Alien();
        a1.setId(101);
        a1.setName("Navin");
        a1.setTech("Java");

        try {
            EntityManagerFactory emf = createEntityManagerFactory("alienDB");
            SessionFactory factory = emf.unwrap(SessionFactory.class);
            Session session = factory.openSession();

            session.persist(a1);

        } catch (Exception e) {
            System.out.println("Excetion: "+ e);
        }
    }
}