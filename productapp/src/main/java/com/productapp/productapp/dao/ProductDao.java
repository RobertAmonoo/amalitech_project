package com.productapp.productapp.dao;

import com.productapp.productapp.service.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<Product> getByPrice(Double price);
}
