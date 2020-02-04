package com.example.library.controllers;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.example.library.services.BookService;
import com.example.library.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * UserController
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("loggedInUser", userService.getLoggedInUser());
        return "users";
    }

    @PostMapping(value = "/users/add")
    public String addUser(@RequestParam @NotNull Map<String, String> user, RedirectAttributes ra) {
        ra.addFlashAttribute("isSuccess", userService.addUser(user));
        return "redirect:/users";
    }

    @PostMapping(value = "/users/delete")
    public String deleteUser(@RequestParam @NotNull String name, RedirectAttributes ra) {
        String loggedInUser = userService.getLoggedInUser();
        ra.addFlashAttribute("isSuccess", userService.deleteUser(name));
        
        if (name.equals(loggedInUser)) {
            return "redirect:/logout";
        } else {
            return "redirect:/users";
        }
    }

}