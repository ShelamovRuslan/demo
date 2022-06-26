package com.example.demo.controlers;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.ExpressionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppController {

    private User user;
    private final ExpressionService expressionService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppController(ExpressionService expressionService,
                         ProductRepository productRepository,
                         UserRepository userRepository){

        this.expressionService = expressionService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all-products")
    public String ShowAll (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(auth.getName());
        model.addAttribute("AllUserProducts",
               productRepository.findAllByUser(user));
        return "result";
    }

    @PostMapping("/okey")
    public String okey(@RequestParam String value){
        System.out.println(value);
        return "redirect:/ok";
    }

    @PostMapping("/add")
    public String addExpression(@RequestParam String name,
                                double protein,
                                double fat,
                                double carbohydrates,
                                double kcal ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(auth.getName());
        Product product = new Product(
                0,
                name,
                protein,
                fat,
                carbohydrates,
                kcal,
                user);
        productRepository.save(product);
        return "redirect:/all-products";
    }

}
