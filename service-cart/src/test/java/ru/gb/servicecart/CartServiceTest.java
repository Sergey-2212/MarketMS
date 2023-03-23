package ru.gb.servicecart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.api.ProductDto;
import ru.gb.servicecart.entities.CartItem;
import ru.gb.servicecart.exceptions.NotFoundException;
import ru.gb.servicecart.integrations.ProductServiceIntegration;
import ru.gb.servicecart.services.CartService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest(classes = CartService.class)
public class CartServiceTest {

    @Autowired
    private CartService cartService;
    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @BeforeEach
    public void initCart() {
        cartService.clear("null");
    }

    @Test
    public void addToCartTest() {
        ProductDto testProductDto = new ProductDto(10L, "Juice", BigDecimal.valueOf(250), "Bakery");
        Mockito.doReturn(testProductDto).when(productServiceIntegration).findProductById(10L);
        cartService.addProduct("null",10L);
        cartService.addProduct("null",10L);


        Assertions.assertEquals(1, cartService.getCurrentCart("null").getItems().size());
        CartItem cartItem = cartService.getCurrentCart("null").getItems().stream().filter(p -> p.getProductId().equals(10L)).findFirst().orElseThrow(() -> new NotFoundException("TestItem not found"));
        Assertions.assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.HALF_UP), cartItem.getTotalPrice());
    }
}
