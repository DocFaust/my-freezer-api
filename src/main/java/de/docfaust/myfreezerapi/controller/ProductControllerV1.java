package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import de.docfaust.myfreezerapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 {

    public final ProductService productService;

    @GetMapping("/")
    public String getAllProducts() {
        return productService.getAllProducts().toString();
    }
}

