package com.example.CustomerManagementApplication.payload.mapper;

import com.example.CustomerManagementApplication.entity.Customer;
import com.example.CustomerManagementApplication.payload.request.CustomerRequest;
import com.example.CustomerManagementApplication.payload.response.CustomerResponse;

public class CustomerMapper {

    public static Customer mapCustomerRequestToEntity(CustomerRequest customerRequest) {
        return Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
                .build();
    }

    public static CustomerResponse mapCustomerToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .build();
    }
    public static void updateCustomerEntityFromRequest(CustomerRequest customerRequest, Customer customer) {
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
    }
}
