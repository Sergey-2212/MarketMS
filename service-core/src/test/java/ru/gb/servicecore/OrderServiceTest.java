package ru.gb.servicecore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.api.CartDto;
import ru.gb.api.CartItemDto;
import ru.gb.servicecore.converters.CartItemToOrderItemConverter;
import ru.gb.servicecore.entities.Order;
import ru.gb.servicecore.repositories.OrderRepository;
import ru.gb.servicecore.services.OrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = OrderService.class)
public class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepositoryTest;
    @Autowired
    private CartItemToOrderItemConverter converter;
    @Autowired
    private OrderService orderService;

    @Test
    public void makeOrderTest() {
        CartDto cartDto = new CartDto();
        List<CartItemDto> cartItemListTest = new ArrayList<>();
        cartItemListTest.add(new CartItemDto(
                10L,
                "Juice",
                BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP),
                BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_UP),
                2));
        cartDto.setItems(cartItemListTest);
        cartDto.setTotalPrice(BigDecimal.valueOf(200).setScale(2,RoundingMode.HALF_UP));

        Mockito.doReturn(1L).when(orderRepositoryTest).saveAndFlush(new Order());
        Assertions.assertEquals(1L, orderService.makeOrder(cartDto, "user"));

    }
}
