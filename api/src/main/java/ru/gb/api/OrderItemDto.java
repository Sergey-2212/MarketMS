package ru.gb.api;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long id;
    private ProductDto product;
    private BigDecimal pricePerItem;
    private Integer quantity;
    private BigDecimal totalPrice;

    private OrderItemDto(Builder builder) {
        setId(builder.id);
        setProduct(builder.product);
        setPricePerItem(builder.pricePerItem);
        setQuantity(builder.quantity);
        setTotalPrice(builder.totalPrice);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public BigDecimal getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(BigDecimal pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public static final class Builder {
        private Long id;
        private ProductDto product;
        private BigDecimal pricePerItem;
        private Integer quantity;
        private BigDecimal totalPrice;

        private Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withProduct(ProductDto val) {
            product = val;
            return this;
        }

        public Builder withPricePerItem(BigDecimal val) {
            pricePerItem = val;
            return this;
        }

        public Builder withQuantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder withTotalPrice(BigDecimal val) {
            totalPrice = val;
            return this;
        }

        public OrderItemDto build() {
            return new OrderItemDto(this);
        }
    }
}
