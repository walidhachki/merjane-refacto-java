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
public class SeasonalProductHandler implements ProductHandler {

    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    @Override
    public ProductType getType() {
        return ProductType.SEASONAL;
    }

    @Override
    public void handle(Product product) {

        LocalDate now = LocalDate.now();

        if (now.isAfter(product.getSeasonStartDate()) &&
                now.isBefore(product.getSeasonEndDate()) &&
                product.getAvailable() > 0) {

            product.setAvailable(product.getAvailable() - 1);
            productRepository.save(product);

        } else {

            if (now.plusDays(product.getLeadTime()).isAfter(product.getSeasonEndDate())) {
                notificationService.sendOutOfStockNotification(product.getName());
                product.setAvailable(0);

            } else if (product.getSeasonStartDate().isAfter(now)) {
                notificationService.sendOutOfStockNotification(product.getName());

            } else {
                notificationService.sendDelayNotification(
                        product.getLeadTime(),
                        product.getName()
                );
            }

            productRepository.save(product);
        }
    }
}
