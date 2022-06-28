package com.example.demo.controlers;


import com.example.demo.entities.LineShoppingList;
import com.example.demo.entities.User;
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
    public final ShoppingListRepository shoppingListRepository;

    public ControllerShoppingList(Calorizator calorizator, UserRepository userRepository,
                                  ShoppingListRepository shoppingListRepository){
        this.calorizator = calorizator;
        this.userRepository = userRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @GetMapping ("/shopping-list")
    public String getShoppingListPage (Model model) {
        ArrayList<LineShoppingList> list = shoppingListRepository
                .findAllByUser(getCurrentUser());
        calorizator.set(list);
        String report = calorizator.getReport();
        model.addAttribute("report", calorizator.getReport());
        model.addAttribute("shoppingList", list);
        return "shopping-list-page";
    }

    @GetMapping("/edite-line")
    public String editeProduct(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(
                        request.getParameter("idSelectLine"));
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
