package com.nimbleways.springboilerplate.services;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.nimbleways.springboilerplate.enums.ProductType;
import com.nimbleways.springboilerplate.services.handler.ProductHandler;
import org.springframework.stereotype.Service;

import com.nimbleways.springboilerplate.entities.Product;


@Service
public class ProductService {

    private final Map<ProductType, ProductHandler> handlers;

    public ProductService(List<ProductHandler> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        ProductHandler::getType,
                        h -> h
                ));
    }

    public void processProduct(Product product) {
        ProductHandler handler = handlers.get(product.getType());

        if (handler == null) {
            throw new IllegalArgumentException("No handler for type: " + product.getType());
        }

        handler.handle(product);
    }
}