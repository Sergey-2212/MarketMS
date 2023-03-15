package ru.gb.servicecart.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.api.ProductDto;
import ru.gb.servicecart.integrations.ProductServiceIntegration;
import ru.gb.servicecart.entities.Cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;

    private Map<String, Cart> carts;

    @PostConstruct
    private void init() {
        carts = new HashMap<>();
    }
    public Cart getTempCart (String uuId) {
        if(carts.containsKey(uuId)) {
            log.info("getTempCart + true  " + carts.size());
            return carts.get(uuId);
        } else {
            Cart cart = new Cart();
            carts.put(uuId, cart);
            log.info("getTempCart + false  " + carts.size());

            return cart;
        }
    }

    public void addProduct(String uuId, Long id) {
        ProductDto productDto = productServiceIntegration.findProductById(id);
        carts.get(uuId).add(productDto);
    }

    public void changeItemQuantityById(String uuId, Long productId, int delta) {
        carts.get(uuId).changeItemQuantityById(productId, delta);
    }

    public void removeItem(String uuId, Long productId) {
            carts.get(uuId).deleteItemByID(productId);
    }

    public void clear(String uuId) {
        carts.get(uuId).deleteAllItems();
    }
}
