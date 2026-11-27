package com.ashwinkumard;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        // Dev dev = context.getBean(Dev.class);
//        Dev dev = (Dev) context.getBean("dev");

        // Don't need to type cast here when we are using getBean(byType) API.
        Dev dev = context.getBean(Dev.class);
        //dev.build();
        // below line will print `dev.age = 12` value 12 comes from `spring.xml` file
//        System.out.println("dev.age = " + dev.getAge());
//        dev.setAge(19);
//        System.out.println("dev.age = " + dev.getAge());

        System.out.println("dev = " + dev);
        System.out.println("dev.laptop = " + dev.getMyComputer());

    }
}
