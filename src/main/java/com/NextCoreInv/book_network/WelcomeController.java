package com.NextCoreInv.book_network;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("appName", "Social Book Network API");
        model.addAttribute("version", "v1.0.0");
        model.addAttribute("description", "Welcome to the Social Book Network API - A platform for book lovers to connect, share, and discover amazing books.");
        return "welcome";
    }
}