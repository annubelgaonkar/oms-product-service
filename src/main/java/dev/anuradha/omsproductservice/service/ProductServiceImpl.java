package dev.anuradha.omsproductservice.service;

import dev.anuradha.omsproductservice.dto.CreateProductRequest;
import dev.anuradha.omsproductservice.entity.Product;
import dev.anuradha.omsproductservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RedisTemplate<String, Object> template;

    private static final String PRODUCT_CACHE = "product";

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
    public Product getProduct(UUID id) {

        String key = PRODUCT_CACHE + id;

        //check the catche first
        Product cached = template.opsForValue().get(key);
        if(cached != null){
            log.info("Product fetched from Redis cache");
            return cached;
        }

        //if not found fetch from DB, then store in cache
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //store in cache
        template.opsForValue().set(key, product);
        return product;


    }
}
