package ru.gb.servicecart.converters;

import org.springframework.stereotype.Component;
import ru.gb.api.CartItemDto;
import ru.gb.servicecart.entities.CartItem;

@Component
public class CartItemConverter {

    public CartItemDto dtoFromEntity (CartItem item) {
//        return new CartItemDto(
//                item.getProductId(),
//                item.getTitle(),
//                item.getPricePerProduct(),
//                item.getTotalPrice(),
//                item.getQuantity()
            return CartItemDto.builder()
                    .withProductId(item.getProductId())
                    .withTitle(item.getTitle())
                    .withPricePerProduct(item.getPricePerProduct())
                    .withTotalPrice(item.getPricePerProduct())
                    .withQuantity(item.getQuantity())
                    .build();

    }
}
