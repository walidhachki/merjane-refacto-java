package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enums.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.ProductService;
import com.nimbleways.springboilerplate.services.handler.ProductHandler;
import com.nimbleways.springboilerplate.utils.Annotations.UnitTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class)
@UnitTest
public class MyUnitTests {

    @Mock
    private NotificationService notificationService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductHandler normalHandler;

    private ProductService productService; // Plus de @InjectMocks

    @Test
    public void test() {
        Product product = new Product(null, 15, 0, ProductType.NORMAL, "RJ45 Cable", null, null, null);

        Mockito.when(normalHandler.getType()).thenReturn(ProductType.NORMAL);

        productService = new ProductService(List.of(normalHandler));

        productService.processProduct(product);

        Mockito.verify(normalHandler, Mockito.times(1)).handle(product);
    }
}