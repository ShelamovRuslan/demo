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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class AppController {


    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppController(ProductRepository productRepository,
                         UserRepository userRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all-products")
    public String ShowAll (Model model) {
        model.addAttribute("AllUserProducts",
               productRepository.findAllByUser(getCurrentUser()));
        return "result";
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByLogin(auth.getName());
    }

    @GetMapping("/delete")
    public String deleteProduct(HttpServletRequest request) {
        Optional<Product> product = productRepository
                .findById(Integer.parseInt(request
                        .getParameter("idSelectProduct")));
        deleteProduct(product);
        return "redirect:/all-products";
    }

    @GetMapping("/edite")
    public String editeProduct(HttpServletRequest request, Model model) {
        Optional<Product> product = productRepository
                .findById(Integer.parseInt(request
                        .getParameter("idSelectProduct")));
        if (product.isPresent()){
            if (getCurrentUser().equals(product.get().getUser())){
                model.addAttribute("product",
                        product.get());
                return "edite-product";
            }
        }
        return "redirect:/all-products";
    }

    @PostMapping("/edite-part-two")
    public String editePartTwo(@RequestParam
    int id,
    String name,
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
                kcal,
                getCurrentUser());
        productRepository.save(product);
        deleteProduct(productRepository.findById(id));
        return "redirect:/all-products";
    }
    @PostMapping("/add")
    public String addUserProduct(@RequestParam
                                String name,
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
                kcal,
                getCurrentUser());
        productRepository.save(product);
        return "redirect:/all-products";
    }

    public void deleteProduct (Optional<Product> product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(auth.getName());
        if (product.isPresent()){
            if (user.equals(product.get().getUser())){
                productRepository.delete(product.get());
            }
        }
    }

}
