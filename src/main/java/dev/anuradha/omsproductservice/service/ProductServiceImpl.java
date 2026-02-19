package dev.anuradha.omsproductservice.service;

import dev.anuradha.omsproductservice.dto.CreateProductRequest;
import dev.anuradha.omsproductservice.entity.Product;
import dev.anuradha.omsproductservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private static final String PRODUCT_CACHE = "product:";

    @Override
    public Product createProduct(CreateProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .category(request.getCategory())
                .build();
        return productRepository.save(product);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Product getProduct(UUID id) {

        log.info("Fetching products from DB for id: {}", id);

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
