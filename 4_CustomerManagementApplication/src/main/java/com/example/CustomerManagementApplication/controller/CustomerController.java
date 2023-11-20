package com.example.CustomerManagementApplication.controller;

import com.example.CustomerManagementApplication.payload.request.CustomerRequest;
import com.example.CustomerManagementApplication.payload.response.CustomerResponse;
import com.example.CustomerManagementApplication.payload.ResponseMessage;
import com.example.CustomerManagementApplication.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/save")
    public ResponseMessage<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.createCustomer(customerRequest);
    }

    @GetMapping("/{id}")
    public ResponseMessage<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getAll")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/page")
    public Page<CustomerResponse> getAllCustomersByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return customerService.getAllCustomersByPage(page, size, sort, direction);
    }

    @PutMapping("/update/{id}")
    public ResponseMessage<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequest customerRequest) {
        return customerService.updateCustomer(id, customerRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}