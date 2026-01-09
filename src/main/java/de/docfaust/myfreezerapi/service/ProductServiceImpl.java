package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Product;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import de.docfaust.myfreezerapi.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::fromEntity).toList();
    }
}
