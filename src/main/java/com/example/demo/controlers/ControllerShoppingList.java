package com.example.demo.controlers;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repository.ShoppingListRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;


@Controller
public class ControllerShoppingList {
    private final UserRepository userRepository;
    public final ShoppingListRepository shoppingListRepository;

    public ControllerShoppingList(UserRepository userRepository,
                                  ShoppingListRepository shoppingListRepository){
        this.userRepository = userRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @GetMapping ("/shopping-list")
    public String getShoppingListPage (Model model) {
        model.addAttribute("shoppingList",
                shoppingListRepository.findAllProductByUser(getCurrentUser())
        );
        return "shopping-list-page";
    }



    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByLogin(auth.getName());
    }
}
