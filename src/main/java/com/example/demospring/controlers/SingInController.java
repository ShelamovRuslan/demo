package com.example.demospring.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SingInController {
    @GetMapping ("/sing-in")
    public String getSingInPage () {
        return "singin_page";
    }

    @PostMapping("/incorrect-password")
    public String incorrectPassword() {
        return "redirect:/sing-up";
    }

}
