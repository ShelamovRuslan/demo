package com.example.demo.repository;

import com.example.demo.entities.DefaultProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DefaultProductRepository extends JpaRepository<DefaultProduct, Integer> {

    List<DefaultProduct> findByName(String name);

}