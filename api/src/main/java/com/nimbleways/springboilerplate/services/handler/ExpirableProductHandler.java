package com.nimbleways.springboilerplate.services.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enums.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExpirableProductHandler implements ProductHandler {

    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public ProductType getType() {
        return ProductType.EXPIRABLE;
    }

    public void handle(Product product) {

        if (product.getAvailable() > 0 &&
                product.getExpiryDate().isAfter(LocalDate.now())) {

            product.setAvailable(product.getAvailable() - 1);
            productRepository.save(product);

        } else {
            notificationService.sendExpirationNotification(
                    product.getName(),
                    product.getExpiryDate()
            );
            product.setAvailable(0);
            productRepository.save(product);
        }
    }
}