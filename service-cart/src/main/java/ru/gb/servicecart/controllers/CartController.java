package ru.gb.servicecart.controllers;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.CartDto;
import ru.gb.api.StringResponse;
import ru.gb.servicecart.services.CartService;
import ru.gb.servicecart.converters.CartConverter;

import java.util.UUID;

@RestController
@Slf4j
@Data
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CartConverter converter;

    @Value("${cart-service.cart-prefix}")
    private String prefix;

    @GetMapping("/generate_id")
    public StringResponse getUuId () {
        log.info("getUuId");
        return new StringResponse(String.format(prefix + UUID.randomUUID()));
    }

    @GetMapping("/{uuId}")
    public CartDto getCart(@RequestHeader(required = false) String username,
                           @PathVariable String uuId) {

        return converter.dtoFromEntity(cartService.getCurrentCart(checkUsername(username, uuId)));
    }
    @GetMapping("/{uuId}/merge")
    public CartDto mergeGuestAndUserCart(@RequestHeader String username,
                                         @PathVariable String uuId) {
        log.info("mergeGuestAndUserCart started " + username + "   " + uuId);
        return converter.dtoFromEntity(cartService.mergeGuestAndUserCart(prefix + username, uuId));
    }

    @GetMapping("/{uuId}/add/{id}")
    public void addProductToCartById (@RequestHeader(required = false) String username,
                                      @PathVariable String uuId,
                                      @PathVariable Long id) {
        log.debug("ID = " + id);
        cartService.addProduct(checkUsername(username, uuId), id);
    }

    @GetMapping("/{uuId}/changequantity")
    public void changeItemQuantityById (@RequestHeader(required = false) String username,
                                        @PathVariable String uuId,
                                        @RequestParam Long productId, @RequestParam Integer delta) {
        cartService.changeItemQuantityById(checkUsername(username, uuId),productId, delta);
    }

    @DeleteMapping("/{uuId}/delete")
    public void removeItemById(@RequestHeader(required = false) String username,
                               @PathVariable String uuId,@RequestParam Long productId) {
        cartService.removeItem(checkUsername(username, uuId), productId);
    }

    @DeleteMapping("/{uuId}/clear")
    public void clearCart(@RequestHeader(required = false) String username,
                          @PathVariable String uuId) {
        cartService.clear(checkUsername(username, uuId));
    }
    private String checkUsername(String username, String uuId) {
        if(username == null) {
            return uuId;
        } else {
            return prefix + username;
        }
    }
}
