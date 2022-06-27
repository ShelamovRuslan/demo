package com.example.demospring.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/user-page")
    public String getUserPage () {
        return "user-page";
    }
}
