package com.example.demospring.repository;

import com.example.demospring.entities.Product;
import com.example.demospring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    ArrayList<Product> findAllByUser(User user);
}