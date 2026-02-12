package org.iths.techstore.Validator;

import org.iths.techstore.Exceptions.ProductNotFoundException;
import org.iths.techstore.Exceptions.ProductNotValidException;
import org.iths.techstore.Model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public void validator(Product product) {
        productNameNotValid(product.getName());
        productCategoryNotValid(product.getCategory());
        productPriceNotValid(product.getPrice());
        productStockQuantityNotValid(product.getStockQuantity());
    }

    public void productNameNotValid(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductNotFoundException("Product name is not valid");
        }
    }

    public void productCategoryNotValid(String category) {
        if (category == null || category.isBlank()) {
            throw new ProductNotFoundException("Category is not valid.");
        }
    }

    public void productPriceNotValid(int price) {
        if (price <= 0) {
            throw new ProductNotValidException("Price can't be nothing.");
        }
        if (price >= 100000) {
            throw new ProductNotValidException("Price is too high.");
        }
    }

    public void productStockQuantityNotValid(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new ProductNotValidException("Stock can't be negative.");
        }
        if (stockQuantity > 100) {
            throw new ProductNotValidException("Stock can't be bigger than 100.");
        }
    }
}

