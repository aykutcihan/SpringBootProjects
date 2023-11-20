package com.example.CustomerManagementApplication.service;

import com.example.CustomerManagementApplication.entity.Order;
import com.example.CustomerManagementApplication.payload.mapper.OrderMapper;
import com.example.CustomerManagementApplication.payload.request.OrderRequest;
import com.example.CustomerManagementApplication.payload.response.OrderResponse;
import com.example.CustomerManagementApplication.payload.ResponseMessage;
import com.example.CustomerManagementApplication.repository.CustomerRepository;
import com.example.CustomerManagementApplication.repository.OrderRepository;
import com.example.CustomerManagementApplication.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    public ResponseMessage<OrderResponse> createOrder(OrderRequest orderRequest) {
        Order order = OrderMapper.mapOrderRequestToEntity(orderRequest, customerRepository, productRepository);
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = OrderMapper.mapOrderToResponse(savedOrder);

        return ResponseMessage.<OrderResponse>builder()
                .object(orderResponse)
                .message("Order successfully created")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<OrderResponse> getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            OrderResponse orderResponse = OrderMapper.mapOrderToResponse(orderOptional.get());
            return ResponseMessage.<OrderResponse>builder()
                    .object(orderResponse)
                    .message("Order retrieved successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<OrderResponse>builder()
                    .object(null)
                    .message("Order not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::mapOrderToResponse)
                .collect(Collectors.toList());
    }

    public Page<OrderResponse> getAllOrdersByPage(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Page<Order> orderPage = orderRepository.findAll(pageRequest);
        return orderPage.map(OrderMapper::mapOrderToResponse);
    }

    public ResponseMessage<OrderResponse> updateOrder(Long id, OrderRequest orderRequest) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            // Burada Order'ı güncelleme işlemi gerçekleştirilecek
            // Örnek: OrderMapper.updateOrderEntityFromRequest(orderRequest, order);
            Order updatedOrder = orderRepository.save(order);
            OrderResponse orderResponse = OrderMapper.mapOrderToResponse(updatedOrder);

            return ResponseMessage.<OrderResponse>builder()
                    .object(orderResponse)
                    .message("Order updated successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<OrderResponse>builder()
                    .object(null)
                    .message("Order not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    public ResponseMessage<?> deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseMessage.<OrderResponse>builder()
                    .object(null)
                    .message("Order deleted successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<OrderResponse>builder()
                    .object(null)
                    .message("Order not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
