package ru.gb.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private String username;
    private String address;
    private String phoneNumber;
    private BigDecimal totalPrice;
    private LocalDateTime ordered_at;
    private List<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Builder builder) {
        setId(builder.id);
        setUsername(builder.username);
        setAddress(builder.address);
        setPhoneNumber(builder.phoneNumber);
        setTotalPrice(builder.totalPrice);
        setOrdered_at(builder.ordered_at);
        setItems(builder.items);
    }

    public static Builder builder() {
        return new Builder();
    }


    public LocalDateTime getOrdered_at() {
        return ordered_at;
    }

    public void setOrdered_at(LocalDateTime ordered_at) {
        this.ordered_at = ordered_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }


    public static final class Builder {
        private Long id;
        private String username;
        private String address;
        private String phoneNumber;
        private BigDecimal totalPrice;
        private LocalDateTime ordered_at;
        private List<OrderItemDto> items;

        private Builder() {
        }

        public Builder withId(Long val) {
            id = val;
            return this;
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withAddress(String val) {
            address = val;
            return this;
        }

        public Builder withPhoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public Builder withTotalPrice(BigDecimal val) {
            totalPrice = val;
            return this;
        }

        public Builder withOrdered_at(LocalDateTime val) {
            ordered_at = val;
            return this;
        }

        public Builder withItems(List<OrderItemDto> val) {
            items = val;
            return this;
        }

        public OrderDto build() {
            return new OrderDto(this);
        }
    }
}

