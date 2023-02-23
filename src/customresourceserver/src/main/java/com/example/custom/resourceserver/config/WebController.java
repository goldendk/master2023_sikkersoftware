package com.example.custom.resourceserver.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class WebController {


    @GetMapping("")
    public String root2(){
        return "index.html";
    }

    @GetMapping("/")
    public String root(){
        return "index.html";
    }


}
