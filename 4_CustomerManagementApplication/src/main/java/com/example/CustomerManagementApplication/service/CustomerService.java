package com.example.CustomerManagementApplication.service;

import com.example.CustomerManagementApplication.entity.Customer;
import com.example.CustomerManagementApplication.payload.mapper.CustomerMapper;
import com.example.CustomerManagementApplication.payload.request.CustomerRequest;
import com.example.CustomerManagementApplication.payload.response.CustomerResponse;
import com.example.CustomerManagementApplication.payload.ResponseMessage;
import com.example.CustomerManagementApplication.repository.CustomerRepository;
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
public class CustomerService {

    private final CustomerRepository customerRepository;
    public ResponseMessage<CustomerResponse> createCustomer(CustomerRequest customerRequest) {

        Optional<Customer> existingCustomer = customerRepository.findByEmail(customerRequest.getEmail());
        if (existingCustomer.isPresent()) {
            return ResponseMessage.<CustomerResponse>builder()
                    .object(null)
                    .message("Customer with this email already exists")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        Customer customer = CustomerMapper.mapCustomerRequestToEntity(customerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerResponse customerResponse = CustomerMapper.mapCustomerToResponse(savedCustomer);

        return ResponseMessage.<CustomerResponse>builder()
                .object(customerResponse)
                .message("Customer successfully created")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }


        public ResponseMessage<CustomerResponse> getCustomerById(Long id) {
            Optional<Customer> customerOptional = customerRepository.findById(id);

            if (customerOptional.isPresent()) {
                CustomerResponse customerResponse = CustomerMapper.mapCustomerToResponse(customerOptional.get());
                return ResponseMessage.<CustomerResponse>builder()
                        .object(customerResponse)
                        .message("Customer retrieved successfully")
                        .httpStatus(HttpStatus.OK)
                        .build();
            } else {
                return ResponseMessage.<CustomerResponse>builder()
                        .object(null)
                        .message("Customer not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build();
                }

            }

        public List<CustomerResponse> getAllCustomers() {
            List<Customer> customers = customerRepository.findAll();
            return customers.stream()
                    .map(CustomerMapper::mapCustomerToResponse)
                    .collect(Collectors.toList());
        }


    public Page<CustomerResponse> getAllCustomersByPage(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Page<Customer> customerPage = customerRepository.findAll(pageRequest);
        return customerPage.map(CustomerMapper::mapCustomerToResponse);
    }

        public ResponseMessage<CustomerResponse> updateCustomer(Long id, CustomerRequest customerRequest) {
            Optional<Customer> customerOptional = customerRepository.findById(id);

            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                CustomerMapper.updateCustomerEntityFromRequest(customerRequest, customer);
                Customer updatedCustomer = customerRepository.save(customer);
                CustomerResponse customerResponse = CustomerMapper.mapCustomerToResponse(updatedCustomer);

                return ResponseMessage.<CustomerResponse>builder()
                        .object(customerResponse)
                        .message("Customer updated successfully")
                        .httpStatus(HttpStatus.OK)
                        .build();
            } else {
                return ResponseMessage.<CustomerResponse>builder()
                        .object(null)
                        .message("Customer not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build();
            }
        }

        public ResponseMessage<?> deleteCustomer(Long id) {
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
                return ResponseMessage.<CustomerResponse>builder()
                        .object(null)
                        .message("Customer deleted successfully")
                        .httpStatus(HttpStatus.OK)
                        .build();
            } else {
                return ResponseMessage.<CustomerResponse>builder()
                        .object(null)
                        .message("Customer not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build();
            }



        }



}
