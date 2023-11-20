package com.example.CustomerManagementApplication.repository;

import com.example.CustomerManagementApplication.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
