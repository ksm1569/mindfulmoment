package com.smsoft.mindfulmoment.presentation.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/survey")
    public String surveyPage() {
        return "survey";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}