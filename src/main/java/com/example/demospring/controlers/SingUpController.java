package com.example.demospring.controlers;

import com.example.demospring.entities.User;
import com.example.demospring.repository.UserRepository;
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


    @GetMapping ("/sing-up")
    public String getSingUpPage () {
        return "sing-up-page";
    }
    @PostMapping("/sing-up")
    public String singUpUser (User user) {
        user.setHashPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/sing-in";
    }
}
