package ru.gb.servicecore.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.api.OrderDto;
import ru.gb.servicecore.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto (Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAddress(order.getAddress());
        orderDto.setPhoneNumber(order.getPhoneNumber());
        orderDto.setUsername(order.getUsername());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setOrdered_at(order.getOrdered_at());
        orderDto.setItems(order.getItems().stream()
                .map(orderItemConverter::entityToDto)
                .collect(Collectors.toList()));
        return orderDto;
    }
}
