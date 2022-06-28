package com.example.demo.controlers;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingListRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;


@Controller
public class AppController {

    private final ShoppingListRepository shoppingListRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppController(ShoppingListRepository shoppingListRepository, ProductRepository productRepository,
                         UserRepository userRepository){
        this.shoppingListRepository = shoppingListRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all-products")
    public String ShowAll (Model model) {
        ArrayList<Product> list = productRepository.findAllByUser(getCurrentUser());
        if (list.isEmpty()){
            return "redirect:/user-page";
        }
        model.addAttribute("AllUserProducts",
                list);
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
        if (product.isPresent()){
            Product productTemp = product.get();
            if (getCurrentUser().equals(productTemp.getUser())){
                deleteAllLineConcreteProduct(productTemp);
                productRepository.delete(product.get());
            }
        }
        return "redirect:/all-products";
    }

    @GetMapping("/edite")
    public String editeProduct(HttpServletRequest request, Model model) {
        Optional<Product> product = productRepository
                .findById(Integer.parseInt(request
                        .getParameter("idSelectProduct")));
        if (product.isPresent()){
            Product productTemp = product.get();
            if (getCurrentUser().equals(productTemp.getUser())){
                model.addAttribute("product",
                        productTemp);
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
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setProtein(protein);
        product.setFat(fat);
        product.setCarbohydrates(carbohydrates);
        product.setKcal(kcal);
        productRepository.save(product);
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

    public void deleteAllLineConcreteProduct (Product product) {
        shoppingListRepository.deleteAllByProduct(product);
    }

}
