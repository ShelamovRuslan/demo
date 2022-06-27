package com.example.demo.repository;

import com.example.demo.entities.Product;
import com.example.demo.entities.ShoppingList;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {

    @Query("select s.product from ShoppingList s where s.user = ?1")
    ArrayList<Product> findAllProductByUser(User user);


}