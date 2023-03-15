package ru.gb.servicecore.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.api.CartItemDto;
import ru.gb.servicecore.entities.Order;
import ru.gb.servicecore.entities.OrderItem;
import ru.gb.servicecore.entities.Product;
import ru.gb.servicecore.exceptions.NotFoundException;
import ru.gb.servicecore.services.ProductService;

@Component
@RequiredArgsConstructor
public class CartItemToOrderItemConverter {

    private final ProductService productService;

    public OrderItem cartItemToOrderItem(CartItemDto item, Order order) {
        Product product = productService.findProductById(item.getProductId()).orElseThrow(() -> new NotFoundException(String.format("Product (id%s) not foind.", item.getProductId())));
        return new OrderItem(
                product,
                item.getPricePerProduct(),
                item.getQuantity(),
                item.getTotalPrice(),
                order);
    }
}
