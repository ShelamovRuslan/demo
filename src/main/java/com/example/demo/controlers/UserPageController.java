package com.example.demo.controlers;

import com.example.demo.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/user-page")
    public String getUserPage () {
        return "user-page";
    }

}
