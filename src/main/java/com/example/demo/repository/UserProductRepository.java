package com.example.demo.repository;

import com.example.demo.entities.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {

    List<Integer> findAllProductIdByUserId(Integer id);
}