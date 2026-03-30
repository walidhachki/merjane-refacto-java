package com.nimbleways.springboilerplate.services.handler;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enums.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExpirableProductHandlerTest {

    @Mock
    NotificationService notificationService;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ExpirableProductHandler handler;

    @Test
    void shouldDecreaseStockWhenNotExpired() {
        Product p = new Product(null, 10, 5, ProductType.EXPIRABLE, "Milk",
                LocalDate.now().plusDays(5), null, null);

        handler.handle(p);

        assertEquals(4, p.getAvailable());
        verify(productRepository).save(p);
    }

    @Test
    void shouldHandleExpiredProduct() {
        Product p = new Product(null, 10, 5, ProductType.EXPIRABLE, "Milk",
                LocalDate.now().minusDays(1), null, null);

        handler.handle(p);

        verify(notificationService).sendExpirationNotification(p.getName(), p.getExpiryDate());
        verify(productRepository).save(p);
        assertEquals(0, p.getAvailable());
    }
}