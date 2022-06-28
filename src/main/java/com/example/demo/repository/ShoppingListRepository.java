package com.example.demo.repository;

import com.example.demo.entities.LineShoppingList;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
@Repository
public interface ShoppingListRepository extends JpaRepository<LineShoppingList, Integer> {

    @Query("select l from LineShoppingList l where l.user = ?1")
    ArrayList<LineShoppingList> findAllByUser(User user);


    @Transactional
    @Modifying
    @Query("delete from LineShoppingList l where l.product = ?1")
    void deleteAllByProduct(Product product);
}