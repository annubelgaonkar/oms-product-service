package dev.anuradha.omsproductservice.controller;

import dev.anuradha.omsproductservice.dto.CreateProductRequest;
import dev.anuradha.omsproductservice.entity.Product;
import dev.anuradha.omsproductservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(
            @RequestBody CreateProductRequest productRequest){
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @GetMapping
    public ResponseEntity<Product> getProduct(
            @PathVariable UUID id){
        return ResponseEntity.ok(productService.getProduct(id));
    }
}
