package ru.gb.servicecore.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.api.CartDto;
import ru.gb.servicecore.converters.CartItemToOrderItemConverter;
import ru.gb.servicecore.repositories.OrderRepository;
import ru.gb.servicecore.entities.Order;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemToOrderItemConverter converter;
    @Transactional
    public Long makeOrder(CartDto cartDto, String username) {
        log.info("OrderService - makeOrder started");
        Order order = new Order(
                username,
                "Street",
                "PhoneNumber",
                cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream().map(p -> converter.cartItemToOrderItem(p,order)).collect(Collectors.toList()));

        return orderRepository.saveAndFlush(order).getId();
    }

    public Collection<Order> findUserOrders(String username) {
        return orderRepository.findByUsername(username);
    }
}
