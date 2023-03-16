package ru.gb.servicecore.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.api.OrderItemDto;
import ru.gb.servicecore.entities.OrderItem;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    private final ProductConverter productConverter;

    public OrderItemDto entityToDto (OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setPricePerItem(orderItem.getPricePerItem());
        orderItemDto.setProduct(productConverter.entityToDtoConverter(orderItem.getProduct()));
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setTotalPrice(orderItem.getTotalPrice());
        return orderItemDto;
    }

}