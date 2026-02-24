package org.iths.techstore.Service;

import org.iths.techstore.Exceptions.ProductNotFoundException;
import org.iths.techstore.Model.Product;
import org.iths.techstore.Repository.ProductRepository;
import org.iths.techstore.Validator.ProductValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public ProductService(ProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        productValidator.validator(product);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            logger.warn("Product not found for update with id: {}", id);
            throw new ProductNotFoundException("Product with id: " + id + " doesn't exist.");
        }
        productValidator.validator(product);
        product.setId(id);
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product with id: {} not found.", id);
                    throw new ProductNotFoundException("Product with id: " + id + " doesn't exist.");
                });
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            logger.warn("Product with id: {} not found for delete.", id);
            throw new ProductNotFoundException("Product with id: " + id + " doesn't exist.");
        }
        productRepository.deleteById(id);
    }
}
