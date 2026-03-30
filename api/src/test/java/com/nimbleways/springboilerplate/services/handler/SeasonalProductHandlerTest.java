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
class SeasonalProductHandlerTest {

    @Mock
    NotificationService notificationService;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    SeasonalProductHandler handler;

    @Test
    void shouldDecreaseStockWhenInSeason() {
        Product p = new Product(null, 10, 5, ProductType.SEASONAL, "Watermelon",
                null, LocalDate.now().minusDays(1), LocalDate.now().plusDays(5));

        handler.handle(p);

        assertEquals(4, p.getAvailable());
        verify(productRepository).save(p);
    }

    @Test
    void shouldHandleOutOfSeason() {
        Product p = new Product(null, 10, 0, ProductType.SEASONAL, "Watermelon",
                null, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20));

        handler.handle(p);

        verify(notificationService).sendOutOfStockNotification(p.getName());
        verify(productRepository).save(p);
    }
}