package ru.gb.servicecart.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gb.api.CartDto;
import ru.gb.servicecart.entities.Cart;

import java.util.stream.Collectors;

@Component
public class CartConverter {

    @Autowired
    private CartItemConverter cartItemConverter;

    public CartDto dtoFromEntity (Cart cart) {
        return new CartDto(
                cart.getTotalPrice(),
                cart.getItems().stream().map(cartItemConverter::dtoFromEntity).collect(Collectors.toList()));

    }
}
