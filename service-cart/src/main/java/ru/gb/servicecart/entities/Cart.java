package ru.gb.servicecart.entities;

import lombok.Data;
import ru.gb.api.ProductDto;
import ru.gb.servicecart.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Data
public class Cart {

    private BigDecimal totalPrice;
    private List<CartItem> items;
    public Cart() {
        items = new ArrayList<>();
    }
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    public void recalculate() {
        totalPrice = new BigDecimal(0);
        for (CartItem item : items) {
          totalPrice = totalPrice.add(item.getTotalPrice().setScale(2, RoundingMode.HALF_UP));
        }
    }
    public void add (ProductDto productDto) {
        for (CartItem item : items) {
            if(Objects.equals(item.getProductId(), productDto.getId())) {
                item.changeQuantity(1);
                recalculate();
                return;
            }
        }
        items.add(new CartItem(productDto.getId(),
                productDto.getTitle(),
                productDto.getPrice(),
                productDto.getPrice(),
                1));
        recalculate();
    }
    public void changeItemQuantityById (Long productId, Integer delta) {
        for (CartItem item: items) {
            if(item.getProductId().equals(productId)) {
                item.changeQuantity(delta);
                recalculate();
                return;
            }
        }

        throw new NotFoundException(String.format(
                "Продукт c ID = %s не найден в корзине. Класс - %s. Метод - changeItemQuantityById (Long productId, Integer delta)", productId, this.getClass().getSimpleName()));
    }

    public void deleteItemByID (Long productId) {
        if(items.removeIf(p -> p.getProductId().equals(productId))) {
            recalculate();
        } else {
            throw new NotFoundException(String.format("Продукт c ID = %s не найден в корзине. Метод - deleteItemByID", productId));

        }
//       for (CartItem item : items) {
//            if(item.getProductId().equals(productId)) {
//                items.remove(item);
//                recalculate();
//                return;
//            }
//        }

    }

    public void deleteAllItems() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }
}
