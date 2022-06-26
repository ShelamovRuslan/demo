package com.example.demo.controlers;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.UserProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserProductRepository;
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

import java.util.Collections;
import java.util.List;


@Controller
public class AppController {

    private User user;
    private final UserProductRepository userProductRepository;
    private final ExpressionService expressionService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppController(UserProductRepository userProductRepository, ExpressionService expressionService,
                         ProductRepository productRepository, com.example.demo.repository.UserRepository userRepository){
        this.userProductRepository = userProductRepository;
        this.expressionService = expressionService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all-products")
    public String ShowAll (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(auth.getName());

        model.addAttribute("AllUserProducts",
               1);
        return "result";
    }



    @PostMapping("/add")
    public String addExpression(@RequestParam String name,
                                double protein,
                                double fat,
                                double carbohydrates,
                                double kcal ){
        Product product = new Product(
                0,
                name,
                protein,
                fat,
                carbohydrates,
                kcal);
        productRepository.save(product);
        return "redirect:/all-products";
    }

}
