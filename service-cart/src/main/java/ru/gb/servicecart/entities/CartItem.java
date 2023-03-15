package ru.gb.servicecart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Data
@AllArgsConstructor
public class CartItem {

    private final Integer MAX_QUANTITY = 100;

    private Long productId;
    private String title;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;
    private Integer quantity;

    public void changeQuantity(Integer delta) {
        if(quantity <= 1 && delta < 0) { return; }
        if(quantity.equals(MAX_QUANTITY) && delta > 0) { return;}
        quantity += delta;
        totalPrice = totalPrice.add(pricePerProduct.setScale(2, RoundingMode.HALF_UP));
    }
}
