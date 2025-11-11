CREATE TABLE products (
    sku VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NULL,
    manufacturer VARCHAR(255) NULL,
    final_price_huf NUMERIC(20, 2) NULL,
    stock BIGINT NULL,
    ean BIGINT NULL,
    updated_at TIMESTAMP NULL,
    source VARCHAR(255) NULL
);
