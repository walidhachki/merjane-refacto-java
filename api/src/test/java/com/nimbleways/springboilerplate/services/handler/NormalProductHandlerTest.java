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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NormalProductHandlerTest {

    @Mock ProductRepository productRepository;
    @Mock NotificationService notificationService;

    @InjectMocks NormalProductHandler handler;

    @Test
    void shouldDecreaseStockWhenAvailable() {
        Product p = new Product(null, 10, 5, ProductType.NORMAL, "USB", null, null, null);

        handler.handle(p);

        assertEquals(4, p.getAvailable());
        verify(productRepository).save(p);
    }

    @Test
    void shouldSendDelayWhenOutOfStock() {
        Product p = new Product(null, 10, 0, ProductType.NORMAL, "USB", null, null, null);

        handler.handle(p);

        verify(notificationService).sendDelayNotification(10, "USB");
    }
}
