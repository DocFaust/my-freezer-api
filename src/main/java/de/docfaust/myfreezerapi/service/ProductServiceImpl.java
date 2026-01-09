package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Product;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import de.docfaust.myfreezerapi.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Default {@link ProductService} implementation backed by a repository.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    /**
     * Repository used to load product data.
     */
    private final ProductRepository productRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::fromEntity).toList();
    }
}
