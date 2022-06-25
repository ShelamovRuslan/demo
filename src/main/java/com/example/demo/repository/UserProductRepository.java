package com.example.demo.repository;

import com.example.demo.entities.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {
}