package com.example.CustomerManagementApplication.service;

import com.example.CustomerManagementApplication.entity.Product;
import com.example.CustomerManagementApplication.payload.mapper.ProductMapper;
import com.example.CustomerManagementApplication.payload.request.ProductRequest;
import com.example.CustomerManagementApplication.payload.response.ProductResponse;
import com.example.CustomerManagementApplication.payload.ResponseMessage;
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
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseMessage<ProductResponse> createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.mapProductRequestToEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        ProductResponse productResponse = ProductMapper.mapProductToResponse(savedProduct);

        return ResponseMessage.<ProductResponse>builder()
                .object(productResponse)
                .message("Product successfully created")
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    public ResponseMessage<ProductResponse> getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            ProductResponse productResponse = ProductMapper.mapProductToResponse(productOptional.get());
            return ResponseMessage.<ProductResponse>builder()
                    .object(productResponse)
                    .message("Product retrieved successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<ProductResponse>builder()
                    .object(null)
                    .message("Product not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapProductToResponse)
                .collect(Collectors.toList());
    }

    public Page<ProductResponse> getAllProductsByPage(int page, int size, String sort, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Page<Product> productPage = productRepository.findAll(pageRequest);
        return productPage.map(ProductMapper::mapProductToResponse);
    }

    public ResponseMessage<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductMapper.updateProductEntityFromRequest(productRequest, product);
            Product updatedProduct = productRepository.save(product);
            ProductResponse productResponse = ProductMapper.mapProductToResponse(updatedProduct);

            return ResponseMessage.<ProductResponse>builder()
                    .object(productResponse)
                    .message("Product updated successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<ProductResponse>builder()
                    .object(null)
                    .message("Product not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    public ResponseMessage<?> deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseMessage.<ProductResponse>builder()
                    .object(null)
                    .message("Product deleted successfully")
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            return ResponseMessage.<ProductResponse>builder()
                    .object(null)
                    .message("Product not found")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
