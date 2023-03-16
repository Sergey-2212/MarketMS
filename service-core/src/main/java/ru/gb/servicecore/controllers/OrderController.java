package ru.gb.servicecore.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.OrderDto;
import ru.gb.servicecore.converters.OrderConverter;
import ru.gb.servicecore.integrations.CartServiceIntegration;
import ru.gb.servicecore.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartServiceIntegration cartService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long makeOrder(@RequestHeader String username) {
        log.info(this.getClass().getSimpleName());
        log.info(username);
        Long id = orderService.makeOrder(cartService.getCart(username), username);
        cartService.clear(username);
        return id;
    }
    @GetMapping("/")
    public List<OrderDto> getOrders (@RequestHeader String username) {
        log.info("getOrders  " + username );
        return orderService
                .findUserOrders(username)
                .stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }
}
