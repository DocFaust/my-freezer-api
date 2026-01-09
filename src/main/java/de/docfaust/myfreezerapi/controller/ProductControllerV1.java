package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for product resources.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 {

    /**
     * Service used for product operations.
     */
    public final ProductService productService;

    /**
     * Returns all products as a stringified list of DTOs.
     *
     * @return string representation of all product DTOs
     */
    @GetMapping("/")
    public String getAllProducts() {
        return productService.getAllProducts().toString();
    }
}
