package com.nimbleways.springboilerplate.services;

import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enums.ProductType;
import com.nimbleways.springboilerplate.services.handler.ProductHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;


import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Test
    void shouldDelegateToCorrectHandler() {
        Product p = new Product(null, 10, 5, ProductType.NORMAL, "USB", null, null, null);

        ProductHandler handler = Mockito.mock(ProductHandler.class);
        Mockito.when(handler.getType()).thenReturn(ProductType.NORMAL);

        ProductService productService = new ProductService(List.of(handler));

        productService.processProduct(p);

        verify(handler).handle(p);
    }
}