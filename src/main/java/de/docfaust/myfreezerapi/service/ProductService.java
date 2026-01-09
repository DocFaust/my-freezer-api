package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.dto.ProductDTO;

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
}
