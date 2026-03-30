package com.nimbleways.springboilerplate.services.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enums.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NormalProductHandler implements ProductHandler{

    private final ProductRepository productRepository;
    private final NotificationService notificationService;

    public ProductType getType() {
        return ProductType.NORMAL;
    }

    @Override
    public void handle(Product p) {

        if (p.getAvailable() > 0) {
            p.setAvailable(p.getAvailable() - 1);
            productRepository.save(p);
        } else if (p.getLeadTime() > 0) {
            notificationService.sendDelayNotification(p.getLeadTime(), p.getName());
        }
    }
}
