-- Add audit fields (created_at, updated_at) to category and product tables
-- These fields track when records are created and last modified

-- Add audit fields to category table
ALTER TABLE category
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Add audit fields to product table
ALTER TABLE product
    ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- Create indexes for audit fields to support queries by creation/modification time
CREATE INDEX idx_category_created_at ON category(created_at);
CREATE INDEX idx_category_updated_at ON category(updated_at);
CREATE INDEX idx_product_created_at ON product(created_at);
CREATE INDEX idx_product_updated_at ON product(updated_at);
