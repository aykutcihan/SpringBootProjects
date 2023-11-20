package com.example.CustomerManagementApplication.payload.mapper;

import com.example.CustomerManagementApplication.entity.Customer;
import com.example.CustomerManagementApplication.entity.Order;
import com.example.CustomerManagementApplication.entity.Product;
import com.example.CustomerManagementApplication.payload.request.OrderRequest;
import com.example.CustomerManagementApplication.payload.response.OrderResponse;
import com.example.CustomerManagementApplication.repository.CustomerRepository;
import com.example.CustomerManagementApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    public static Order mapOrderRequestToEntity(OrderRequest orderRequest, CustomerRepository customerRepository, ProductRepository productRepository) {
        Order order = new Order();
        // Müşteri bilgilerini ayarla
        Customer customer = customerRepository.findById(orderRequest.getCustomerId()).orElse(null);
        order.setCustomer(customer);
        order.setOrderDate(orderRequest.getOrderDate());

        // Ürün bilgilerini ayarla
        Set<Product> products = orderRequest.getProductIds().stream()
                .map(productId -> productRepository.findById(productId).orElse(null))
                .collect(Collectors.toSet());
        order.setProducts(products);

        return order;
    }

    public static OrderResponse mapOrderToResponse(Order order) {
        Set<Long> productIds = order.getProducts() != null ? order.getProducts().stream().map(Product::getId).collect(Collectors.toSet()) : new HashSet<>();
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .orderDate(order.getOrderDate())
                .productIds(productIds)
                .build();
    }
    public static void updateOrderEntityFromRequest(OrderRequest orderRequest, Order order, CustomerRepository customerRepository, ProductRepository productRepository) {
        Optional<Customer> customer = customerRepository.findById(orderRequest.getCustomerId());
        customer.ifPresent(order::setCustomer);

        order.setOrderDate(orderRequest.getOrderDate());

        if (orderRequest.getProductIds() != null && !orderRequest.getProductIds().isEmpty()) {
            Set<Product> products = orderRequest.getProductIds().stream()
                    .map(productRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            order.setProducts(products);
        }


    }
}
