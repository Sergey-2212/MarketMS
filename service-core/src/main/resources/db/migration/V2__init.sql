CREATE TABLE IF NOT EXISTS orders
    (id BIGSERIAL primary key,
    username VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(13),
    total_price numeric(8, 2) NOT NULL ,
    ordered_at TIMESTAMP NOT NULL DEFAULT (CURRENT_TIMESTAMP));

CREATE TABLE IF NOT EXISTS ordered_items
    (id BIGSERIAL primary key,
     product_id BIGINT NOT NULL,
     price_per_item DECIMAL NOT NULL,
     quantity INTEGER NOT NULL,
     total_price numeric(8, 2) NOT NULL,
     order_id BIGINT NOT NULL,
     FOREIGN KEY (product_id) REFERENCES products (id),
     FOREIGN KEY (order_id) REFERENCES orders(id));