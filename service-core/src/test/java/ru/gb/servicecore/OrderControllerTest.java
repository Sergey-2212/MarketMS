package ru.gb.servicecore;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.gb.api.CartDto;
import ru.gb.api.CartItemDto;
import ru.gb.servicecore.integrations.CartServiceIntegration;
import ru.gb.servicecore.services.OrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OrderControllerTest {

    @MockBean
    private OrderService orderServiceTest;
    @MockBean
    private CartServiceIntegration cartServiceIntegrationTest;

    @Autowired
    private MockMvc mvc;

    @Test
    private void makeOrder() throws Exception {
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
        Mockito.doReturn(1L).when(orderServiceTest).makeOrder(cartDto, "Bob");
        Mockito.doReturn(cartDto).when(cartServiceIntegrationTest).getCart("null");
        Mockito.doNothing().when(cartServiceIntegrationTest).clear("null");

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/order")
                .accept(MediaType.APPLICATION_JSON)
                .header("username", "user"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNumber());

    }
}
