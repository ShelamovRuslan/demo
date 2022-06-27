package com.example.demo.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductPageController {
    @GetMapping("/add-product")
    public String getAddProductPage () {
        return "add-product";
    }
}
