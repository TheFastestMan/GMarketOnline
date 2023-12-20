CREATE TABLE users
(
    user_id  BIGSERIAL PRIMARY KEY,
    username VARCHAR(50)         NOT NULL,
    password VARCHAR(100)        NOT NULL,
    email    VARCHAR(100) UNIQUE NOT NULL,
    role     VARCHAR(10)         NOT NULL,
    gender   VARCHAR(10)         NOT NULL
    -- Other user-related fields
);
CREATE TABLE carts
(
    cart_id    BIGSERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES users (user_id) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp
);
CREATE TABLE products
(
    product_id   BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(100)   NOT NULL,
    description  TEXT,
    price        DECIMAL(10, 2) NOT NULL,
    quantity     INT            NOT NULL

    -- Other product-related fields
);

CREATE TABLE users_products
(
    user_product_id BIGSERIAL PRIMARY KEY,
    user_id         BIGINT references users (user_id)       NOT NULL,
    product_id      BIGINT references products (product_id) NOT NULL
    -- Other product-related fields
);

CREATE TABLE carts_items
(
    cart_item_id BIGSERIAL PRIMARY KEY,
    cart_id      BIGINT REFERENCES carts (cart_id)       NOT NULL,
    product_id   BIGINT REFERENCES products (product_id) NOT NULL,
    quantity     INT                                     NOT NULL
    -- Other cart item-related fields
);

drop table users CASCADE;
drop table carts_items CASCADE;
drop table products CASCADE;
drop table users_products CASCADE;
drop table carts CASCADE;
drop table products_aud CASCADE ;
drop table revinfo CASCADE ;