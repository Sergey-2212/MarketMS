package ru.gb.servicecart.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.api.ProductDto;
import ru.gb.servicecart.entities.CartItem;
import ru.gb.servicecart.integrations.ProductServiceIntegration;
import ru.gb.servicecart.entities.Cart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            Cart cart = new Cart();
            redisTemplate.opsForValue().set(cartKey, cart);
        }
        return (Cart)redisTemplate.opsForValue().get(cartKey);
    }


    public void addProduct(String cartKey, Long id) {
        //Если просто через get достать корзину из редиса, а потом изменять её, изменения в хранилище не отразятся
        //т.к. транзакции будут не связаны между собой.
        Cart tempCart = getCurrentCart(cartKey);
        for (CartItem item : tempCart.getItems()) {
            if(Objects.equals(item.getProductId(), id)) {
                execute(cartKey, cart -> {
                    cart.changeItemQuantityById(id, 1);
                });
                return;
            }
        }

        execute(cartKey, cart -> {
            ProductDto productDto = productServiceIntegration.findProductById(id);
            cart.addNewProduct(productDto);
        });

    }

    public void changeItemQuantityById(String cartKey, Long productId, int delta) {
        execute(cartKey, c -> {
            c.changeItemQuantityById(productId, delta);
        });
    }

    public void removeItem(String cartKey, Long productId) {
        execute(cartKey, cart -> {
            cart.deleteItemByID(productId);
        });
    }

    public void clear(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartId, cart);
    }

    public Cart mergeGuestAndUserCart(String prefixedUsername, String uuId) {
        log.info("mergeGuestAndUserCart started");
        Cart guestCart = getCurrentCart(uuId);
        log.info("mergeGuestAndUserCart guestCart.totalPrice = " + guestCart.getTotalPrice());
        if(guestCart.getTotalPrice().equals(BigDecimal.ZERO)) {
            return (Cart) redisTemplate.opsForValue().get(prefixedUsername);
        }
        Cart userCart = getCurrentCart(prefixedUsername);
        log.info("mergeGuestAndUserCart userCart.totalPrice  before merging = " + userCart.getTotalPrice());
        userCart.getItems().addAll(guestCart.getItems());
        userCart.recalculate();
        redisTemplate.opsForValue().set(uuId, new Cart());
        redisTemplate.opsForValue().set(prefixedUsername, userCart);
        log.info("mergeGuestAndUserCart userCart.totalPrice  after merging = " + userCart.getTotalPrice());

        return getCurrentCart(prefixedUsername);
    }
}
