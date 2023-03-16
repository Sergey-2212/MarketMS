package ru.gb.servicecart.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Long productId;
    private String title;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;
    private Integer quantity;



    //TODO протестировать уменьшение количества
    public void changeQuantity(Integer delta) {
        if (quantity <= 1 && delta < 0) {
            return;
        }
        quantity += delta;
        if (delta > 0) {
            totalPrice = totalPrice.add(pricePerProduct.setScale(2, RoundingMode.HALF_UP));
        } else {
            totalPrice = totalPrice.subtract(pricePerProduct.setScale(2, RoundingMode.HALF_UP));
        }
    }
}

