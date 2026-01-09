package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.entity.Product;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Default {@link ProductService} implementation backed by a repository.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    /**
     * Repository used to load product data.
     */
    private final ProductRepository productRepository;

    /**
     * Repository used to load category data.
     */
    private final CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        log.debug("Fetching all products");
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDTO::fromEntity).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDTO getProductById(final Long id) {
        log.debug("Fetching product {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
        return ProductDTO.fromEntity(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDTO createProduct(final ProductRequest request) {
        log.info("Creating product {}", request.getName());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Category {} not found for product creation", request.getCategoryId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
                });
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .build();
        Product saved = productRepository.save(product);
        return ProductDTO.fromEntity(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDTO updateProduct(final Long id, final ProductRequest request) {
        log.info("Updating product {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Product {} not found for update", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
                });
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    log.warn("Category {} not found for product update {}", request.getCategoryId(), id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
                });
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        Product saved = productRepository.save(product);
        return ProductDTO.fromEntity(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProduct(final Long id) {
        log.info("Deleting product {}", id);
        if (!productRepository.existsById(id)) {
            log.warn("Product {} not found for deletion", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }
}
