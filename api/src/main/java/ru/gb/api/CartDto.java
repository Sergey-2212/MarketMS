package ru.gb.api;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {

    private BigDecimal totalPrice;
    private List<CartItemDto> items;

    public CartDto() {
    }

    public CartDto(BigDecimal totalPrice, List<CartItemDto> items) {
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
