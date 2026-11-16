package com.ashwinkumard.spring_boot_gradle_app;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
public class TeluskoController {
    
    @GetMapping("/")
    public String getInfo() {
        return "Visit Gradle Telusko App";
    }
}
