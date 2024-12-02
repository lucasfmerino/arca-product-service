CREATE TABLE t_product (
    id BINARY(16) NOT NULL,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255) UNIQUE NOT NULL,
    price DECIMAL(10,2)
    quantity INTEGER,
    is_active BOOLEAN,
    created_at DATETIME,
    updated_at DATETIME,
    deleted_at DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE product_categories (
    product_id BINARY(16) NOT NULL,
    category VARCHAR(255) NOT NULL,
    CONSTRAINT fk_product_category_product_id FOREIGN KEY (product_id) REFERENCES t_product(id) ON DELETE CASCADE
);