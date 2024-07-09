package com.smsoft.mindfulmoment.presentation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/survey")
    public String survey() {
        return "survey";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}