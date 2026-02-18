package dev.anuradha.omsproductservice.service;

import dev.anuradha.omsproductservice.dto.CreateProductRequest;
import dev.anuradha.omsproductservice.entity.Product;

import java.util.UUID;

public interface ProductService {

    Product createProduct(CreateProductRequest request);
    Product getProduct(UUID id);
}
