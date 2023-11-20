package com.example.CustomerManagementApplication.controller;

import com.example.CustomerManagementApplication.payload.request.ProductRequest;
import com.example.CustomerManagementApplication.payload.response.ProductResponse;
import com.example.CustomerManagementApplication.payload.ResponseMessage;
import com.example.CustomerManagementApplication.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseMessage<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/{id}")
    public ResponseMessage<ProductResponse> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getAll")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/page")
    public Page<ProductResponse> getAllProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        return productService.getAllProductsByPage(page, size, sort, direction);
    }

    @PutMapping("/update/{id}")
    public ResponseMessage<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseMessage<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
