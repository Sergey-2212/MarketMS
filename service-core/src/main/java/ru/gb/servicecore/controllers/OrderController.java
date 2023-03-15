package ru.gb.servicecore.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.servicecore.integrations.CartServiceIntegration;
import ru.gb.servicecore.services.OrderService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final CartServiceIntegration cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long makeOrder(@RequestHeader String username) {
        log.info(this.getClass().getSimpleName());
        log.info(username);
        Long id = orderService.makeOrder(cartService.getCart(username), username);
        cartService.clear(username);
        return id;
    }
}
