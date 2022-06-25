package com.example.demo.controlers;

import com.example.demo.entities.UserProduct;
import com.example.demo.repository.DefaultProductRepository;
import com.example.demo.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.ExpressionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppController {

    private final DefaultProductRepository defaultProductRepository;
    private final ExpressionService expressionService;

    private final UserProductRepository userProductRepository;

    @Autowired
    public AppController(DefaultProductRepository defaultProductRepository,
                         ExpressionService expressionService,
                         UserProductRepository userProductRepository) {
        this.defaultProductRepository = defaultProductRepository;

        this.expressionService = expressionService;
        this.userProductRepository = userProductRepository;
    }

    @GetMapping("/all-products")
    public String ShowAll (Model model) {
        model.addAttribute("AllUserProducts",
                userProductRepository.findAll());
        return "result";
    }

    @PostMapping("/add")
    public String addExpression(@RequestParam String name,
                                double protein,
                                double fat,
                                double carbohydrates,
                                double kcal ){
        UserProduct userProduct = new UserProduct(
                0,
                name,
                protein,
                fat,
                carbohydrates,
                kcal);
        userProductRepository.save(userProduct);
        return "redirect:/all-products";
    }

}
