package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.dto.ProductRequest;
import de.docfaust.myfreezerapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ProductControllerV1}.
 */
@ExtendWith(MockitoExtension.class)
class ProductControllerV1Test {

    /**
     * Mocked service dependency for the controller.
     */
    @Mock
    private ProductService productService;

    /**
     * Controller under test with injected mocks.
     */
    @InjectMocks
    private ProductControllerV1 productController;

    /**
     * Verifies that the controller returns product DTOs.
     */
    @Test
    void getAllProducts_returnsDtos() {
        ProductDTO product = ProductDTO.builder()
                .id(2L)
                .name("Fish sticks")
                .description("Breaded")
                .categoryName("Seafood")
                .build();
        List<ProductDTO> products = List.of(product);
        when(productService.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> result = productController.getAllProducts();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(products);
    }

    @Test
    void getProductById_returnsDto() {
        ProductDTO product = ProductDTO.builder()
                .id(12L)
                .name("Pizza")
                .description("Four cheese")
                .categoryName("Ready Meals")
                .build();
        when(productService.getProductById(12L)).thenReturn(product);

        ResponseEntity<ProductDTO> result = productController.getProductById(12L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(product);
    }

    @Test
    void createProduct_returnsCreatedDto() {
        ProductRequest request = ProductRequest.builder()
                .name("Sorbet")
                .description("Lemon")
                .categoryId(3L)
                .build();
        ProductDTO created = ProductDTO.builder()
                .id(9L)
                .name("Sorbet")
                .description("Lemon")
                .categoryName("Desserts")
                .build();
        when(productService.createProduct(request)).thenReturn(created);

        ResponseEntity<ProductDTO> result = productController.createProduct(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(created);
    }

    @Test
    void updateProduct_returnsUpdatedDto() {
        ProductRequest request = ProductRequest.builder()
                .name("Waffles")
                .description("Vanilla")
                .categoryId(4L)
                .build();
        ProductDTO updated = ProductDTO.builder()
                .id(11L)
                .name("Waffles")
                .description("Vanilla")
                .categoryName("Desserts")
                .build();
        when(productService.updateProduct(11L, request)).thenReturn(updated);

        ResponseEntity<ProductDTO> result = productController.updateProduct(11L, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(updated);
    }

    @Test
    void deleteProduct_returnsNoContent() {
        ResponseEntity<Void> result = productController.deleteProduct(5L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(productService).deleteProduct(5L);
    }
}
