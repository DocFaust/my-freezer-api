package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.dto.ProductRequest;

import java.util.List;

/**
 * Service interface for product operations.
 */
public interface ProductService {
    /**
     * Retrieves all products.
     *
     * @return list of product DTOs
     */
    List<ProductDTO> getAllProducts();

    /**
     * Retrieves a single product by identifier.
     *
     * @param id product identifier
     * @return product DTO
     */
    ProductDTO getProductById(Long id);

    /**
     * Creates a new product.
     *
     * @param request product request payload
     * @return created product DTO
     */
    ProductDTO createProduct(ProductRequest request);

    /**
     * Updates an existing product.
     *
     * @param id      product identifier
     * @param request product request payload
     * @return updated product DTO
     */
    ProductDTO updateProduct(Long id, ProductRequest request);

    /**
     * Deletes a product by identifier.
     *
     * @param id product identifier
     */
    void deleteProduct(Long id);
}
