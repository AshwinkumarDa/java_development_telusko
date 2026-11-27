package com.ashwinkumard.MyApp;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// This is type (Laptop) type is declared in Dev class, use it will @Autowired in Dev class.

@Component // Initialize instance in Spring Boot Container.
@Primary // there are 2 classes conforms to Computer Spring Boot will give priority to this @Primary instance to create instance in Bean/ Context.
public class Laptop implements Computer {

    @Override
    public void compile() {
        System.out.println("Compiling with 404 bugs on Laptop");
    }
}
