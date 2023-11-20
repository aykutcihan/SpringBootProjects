package com.example.CustomerManagementApplication.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@Getter
@Setter
public class OrderRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @PastOrPresent(message = "Order date must be in the past or present")
    private Date orderDate;

    @NotNull(message = "Product IDs are required")
    private Set<Long> productIds;
}
