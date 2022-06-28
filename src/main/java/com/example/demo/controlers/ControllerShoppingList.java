package com.example.demo.controlers;


import com.example.demo.entities.LineShoppingList;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingListRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Calorizator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;


@Controller
public class ControllerShoppingList {

    private final Calorizator calorizator;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public final ShoppingListRepository shoppingListRepository;

    public ControllerShoppingList(Calorizator calorizator, UserRepository userRepository,
                                  ProductRepository productRepository, ShoppingListRepository shoppingListRepository){
        this.calorizator = calorizator;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @GetMapping ("/shopping-list")
    public String getShoppingListPage (Model model) {
        ArrayList<LineShoppingList> list = shoppingListRepository
                .findAllByUser(getCurrentUser());
        if (list.isEmpty()){
           return "redirect:/user-page";
        }
        calorizator.set(list);
        model.addAttribute("SumKcal", calorizator.getSumKcal());
        model.addAttribute("SumProtein", calorizator.getSumProtein());
        model.addAttribute("SumFat", calorizator.getSumFat());
        model.addAttribute("SumCarbohydrates", calorizator.getSumCarbohydrates());
        model.addAttribute("ProportionProtein", calorizator.getProportionProtein());
        model.addAttribute("ProportionFat", calorizator.getProportionFat());
        model.addAttribute("ProportionCarbohydrates", calorizator.getProportionCarbohydrates());
        model.addAttribute("shoppingList", list);
        return "shopping-list-page";
    }

    @GetMapping("/edite-line")
    public String editeProduct(HttpServletRequest request, Model model) {
        Optional<LineShoppingList> selectLine = shoppingListRepository
                .findById(Integer.parseInt(
                 request.getParameter("idSelectLine")));
        if (selectLine.isEmpty()) {
            return "redirect:/user-page";
        }
        if (getCurrentUser().equals(selectLine.get().getUser())){
                model.addAttribute("line",
                        selectLine.get());
                return "edite-line";
        }
        return "redirect:/user-page";
    }
    @GetMapping("/add-line")
    public String editeProduct(HttpServletRequest request) {

        Optional<Product> product = productRepository
                .findById(Integer.parseInt(
                        request.getParameter("idSelectProduct")));
        if (product.isEmpty()) {
            return "redirect:/user-page";
        }
        if (getCurrentUser().equals(product.get().getUser())){
            shoppingListRepository.save(new LineShoppingList(0,
                    0,product.get(), getCurrentUser()));
            return "redirect:/all-products";
        }
        return "redirect:/user-page";
    }
    @GetMapping("/delete-line")
    public String deleteLine(HttpServletRequest request) {
        Optional<LineShoppingList> selectLine = shoppingListRepository
                .findById(Integer.parseInt(
                        request.getParameter("idSelectLine")));
        if (selectLine.isEmpty()) {
            return "redirect:/user-page";
        }
        if (getCurrentUser().equals(selectLine.get().getUser())){
            shoppingListRepository.delete(selectLine.get());
            return "redirect:/shopping-list";
        }
        return "redirect:/user-page";
    }

   @PostMapping("/edite-two")
    public String editePartTwo(@RequestParam
                              int id, int quantity ){
     LineShoppingList line = shoppingListRepository.findById(id).orElseThrow();
     line.setQuantity(quantity);
     shoppingListRepository.save(line);
     return "redirect:/shopping-list";
    }



    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByLogin(auth.getName());
    }
}
