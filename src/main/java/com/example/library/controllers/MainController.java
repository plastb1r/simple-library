package com.example.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MainController
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/books";
    }

}