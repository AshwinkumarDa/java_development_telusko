package com.ashwinkumard.MyApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// When we add @Component annotation Spring Boot will create an instance of class and we can access its reference form Context using getBean() API.
@Component
public class Dev {

    // 1. Field Injection
//    @Autowired
//    private Laptop laptop;

    // Field Injection
    @Autowired
    // Use @Qualifier when multiple classes are conforms to same Interface, we are using interface type here.
    // all classes are Primary Component then we can use @Qualifier with instance name.
    @Qualifier("laptop")
    private Computer computer;

//    // 2. Constructor Injection
//    public Dev(Laptop laptop) {
//        this.laptop = laptop;
//    }
//
//    // 3. Setter Injection
//    @Autowired
//    public void setLaptop(Laptop laptop) {
//        this.laptop = laptop;
//    }



    public void build() {
        System.out.println("Build application started");
        computer.compile();
        System.out.println("Working on Awesome Project");
     }
}
