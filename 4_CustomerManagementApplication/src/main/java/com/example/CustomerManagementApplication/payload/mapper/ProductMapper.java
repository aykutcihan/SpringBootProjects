package com.example.CustomerManagementApplication.payload.mapper;

import com.example.CustomerManagementApplication.entity.Product;
import com.example.CustomerManagementApplication.payload.request.ProductRequest;
import com.example.CustomerManagementApplication.payload.response.ProductResponse;

public class ProductMapper {

    public static Product mapProductRequestToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        return product;
    }

    public static ProductResponse mapProductToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }

    public static void updateProductEntityFromRequest(ProductRequest productRequest, Product product) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
    }
}
