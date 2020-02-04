package com.example.library.controllers;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.example.library.services.BookService;
import com.example.library.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping("/books")
    public String getBooksPage(@RequestParam(name = "pages", required = false, defaultValue = "1") Integer pages,
            Model model) {
        model.addAttribute("books", bookService.getBooks(pages));
        model.addAttribute("loggedInUser", userService.getLoggedInUser());
        model.addAttribute("nextPage", ++pages);
        return "books";
    }

    @PostMapping(value = "/books/add")
    public String addBook(@RequestParam @NotNull Map<String, String> book, RedirectAttributes ra) {
        ra.addFlashAttribute("isSuccess", bookService.addBook(book));
        return "redirect:/books";
    }

    @PostMapping(value = "/books/take")
    public String takeBook(@RequestParam @NotNull String isn,RedirectAttributes ra) {
        ra.addFlashAttribute("isSuccess", bookService.takeBook(isn, userService.getLoggedInUser()));
        return "redirect:/books";
    }

    @PostMapping(value = "/books/return")
    public String returnBook(@RequestParam @NotNull String isn, RedirectAttributes ra) {
        ra.addFlashAttribute("isSuccess", bookService.returnBook(isn, userService.getLoggedInUser()));
        return "redirect:/books";
    }

    @PostMapping(value = "/books/delete")
    public String deleteBook(@RequestParam @NotNull String isn, RedirectAttributes ra) {
        ra.addFlashAttribute("isSuccess", bookService.deleteBook(isn));
        return "redirect:/books";
    }

}
