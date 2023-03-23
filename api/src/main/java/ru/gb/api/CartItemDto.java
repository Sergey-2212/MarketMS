package ru.gb.api;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartItemDto {

    private Long productId;
    private String title;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;
    private Integer quantity;

    public CartItemDto() {
    }

    public CartItemDto(Long productId, String title, BigDecimal pricePerProduct, BigDecimal totalPrice, int quantity) {
        this.productId = productId;
        this.title = title;
        this.pricePerProduct = pricePerProduct.setScale(2, RoundingMode.HALF_UP);
        this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        this.quantity = quantity;
    }

    private CartItemDto(Builder builder) {
        setProductId(builder.productId);
        setTitle(builder.title);
        setPricePerProduct(builder.pricePerProduct);
        setTotalPrice(builder.totalPrice);
        setQuantity(builder.quantity);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public static final class Builder {
        private Long productId;
        private String title;
        private BigDecimal pricePerProduct;
        private BigDecimal totalPrice;
        private Integer quantity;

        private Builder() {
        }


        public Builder withProductId(Long val) {
            productId = val;
            return this;
        }

        public Builder withTitle(String val) {
            title = val;
            return this;
        }

        public Builder withPricePerProduct(BigDecimal val) {
            pricePerProduct = val;
            return this;
        }

        public Builder withTotalPrice(BigDecimal val) {
            totalPrice = val;
            return this;
        }

        public Builder withQuantity(Integer val) {
            quantity = val;
            return this;
        }

        public CartItemDto build() {
            return new CartItemDto(this);
        }
    }
}
