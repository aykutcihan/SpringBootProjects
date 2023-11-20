package com.example.CustomerManagementApplication.repository;

import com.example.CustomerManagementApplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
