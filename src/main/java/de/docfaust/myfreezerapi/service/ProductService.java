package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
}
