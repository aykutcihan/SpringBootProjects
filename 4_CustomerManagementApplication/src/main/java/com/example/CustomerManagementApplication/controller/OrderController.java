package com.example.CustomerManagementApplication.controller;


import com.example.CustomerManagementApplication.payload.request.OrderRequest;
import com.example.CustomerManagementApplication.payload.response.OrderResponse;
import com.example.CustomerManagementApplication.payload.ResponseMessage;
import com.example.CustomerManagementApplication.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseMessage<OrderResponse> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{id}")
    public ResponseMessage<OrderResponse> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/getAll")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/page")
    public Page<OrderResponse> getAllOrdersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return orderService.getAllOrdersByPage(page, size, sort, direction);
    }

    @PutMapping("/update/{id}")
    public ResponseMessage<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody @Valid OrderRequest orderRequest) {
        return orderService.updateOrder(id, orderRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrder(id);
    }
}
