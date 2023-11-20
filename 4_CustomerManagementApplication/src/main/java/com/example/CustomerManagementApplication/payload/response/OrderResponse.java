package com.example.CustomerManagementApplication.payload.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long customerId;
    private Date orderDate;
    private Set<Long> productIds;
}
