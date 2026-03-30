package com.nimbleways.springboilerplate.services.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enums.ProductType;

public interface ProductHandler {
    void handle(Product product);
    ProductType getType();
}
