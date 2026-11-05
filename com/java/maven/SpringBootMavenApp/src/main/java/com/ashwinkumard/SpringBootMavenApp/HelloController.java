package com.ashwinkumard.SpringBootMavenApp;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/welcome")
    public String welcomePage() {

        return "index";
    }
}
