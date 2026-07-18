package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.dto.ProductRequest;
import de.docfaust.myfreezerapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Products", description = "Operations for freezer products")
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
    @Operation(summary = "List all products")
    @ApiResponse(responseCode = "200", description = "Products returned successfully")
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
    @Operation(summary = "Get a product by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "Product identifier", required = true)
            @PathVariable final Long id
    ) {
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
    @Operation(summary = "Create a product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
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
    @Operation(summary = "Update a product")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "Product identifier", required = true)
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
    @Operation(summary = "Delete a product")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product identifier", required = true)
            @PathVariable final Long id
    ) {
        log.info("Handling delete product request {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
