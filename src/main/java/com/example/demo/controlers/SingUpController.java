package com.example.demo.controlers;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SingUpController {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SingUpController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping ("/singup")
    public String getSingUpPage () {
        return "singup_page";
    }
    @PostMapping("/singup")
    public String singUpUser (User user) {
        user.setHashPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/singup";
    }
}
