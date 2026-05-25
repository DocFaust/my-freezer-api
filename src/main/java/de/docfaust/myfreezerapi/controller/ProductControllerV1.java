package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.dto.ProductRequest;
import de.docfaust.myfreezerapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for product resources.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductControllerV1 {

    /**
     * Service used for product operations.
     */
    private final ProductService productService;

    /**
     * Returns all products as DTOs.
     *
     * @return list of product DTOs
     */
    @GetMapping({"", "/"})
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.debug("Handling get all products request");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Returns a single product by identifier.
     *
     * @param id product identifier
     * @return product DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable final Long id) {
        log.debug("Handling get product request {}", id);
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Creates a new product.
     *
     * @param request product request payload
     * @return created product DTO
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody final ProductRequest request) {
        log.info("Handling create product request");
        ProductDTO created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing product.
     *
     * @param id      product identifier
     * @param request product request payload
     * @return updated product DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable final Long id,
            @Valid @RequestBody final ProductRequest request
    ) {
        log.info("Handling update product request {}", id);
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    /**
     * Deletes a product by identifier.
     *
     * @param id product identifier
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        log.info("Handling delete product request {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
