package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
     * Verifies that the controller returns a stringified list of product DTOs.
     */
    @Test
    void getAllProducts_returnsStringifiedDtos() {
        ProductDTO product = ProductDTO.builder()
                .id(2L)
                .name("Fish sticks")
                .description("Breaded")
                .categoryName("Seafood")
                .build();
        List<ProductDTO> products = List.of(product);
        when(productService.getAllProducts()).thenReturn(products);

        String result = productController.getAllProducts();

        assertThat(result).isEqualTo(products.toString());
    }
}
