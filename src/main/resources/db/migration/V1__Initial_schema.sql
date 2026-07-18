-- Initial database schema for MyFreezer API
-- This migration creates the category and product tables

-- Create category table
CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);

-- Create product table
CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    category_id BIGINT NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Create indexes for better query performance
CREATE INDEX idx_product_category_id ON product(category_id);
CREATE INDEX idx_category_name ON category(name);
