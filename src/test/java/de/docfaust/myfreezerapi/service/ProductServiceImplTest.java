package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.entity.Product;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import de.docfaust.myfreezerapi.dto.ProductDTO;
import de.docfaust.myfreezerapi.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ProductServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    /**
     * Mock repository used by the service under test.
     */
    @Mock
    private ProductRepository productRepository;

    /**
     * Mock repository used for category lookups.
     */
    @Mock
    private CategoryRepository categoryRepository;

    /**
     * Service under test with injected mocks.
     */
    @InjectMocks
    private ProductServiceImpl productService;

    /**
     * Verifies that entities are mapped to DTOs when fetching products.
     */
    @Test
    void getAllProducts_mapsEntitiesToDtos() {
        Category category = Category.builder()
                .id(10L)
                .name("Vegetables")
                .build();
        Product product = Product.builder()
                .id(5L)
                .name("Frozen peas")
                .description("1kg bag")
                .category(category)
                .build();
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> result = productService.getAllProducts();

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(5L);
                    assertThat(dto.getName()).isEqualTo("Frozen peas");
                    assertThat(dto.getDescription()).isEqualTo("1kg bag");
                    assertThat(dto.getCategoryName()).isEqualTo("Vegetables");
                });
    }

    @Test
    void getProductById_returnsDto() {
        Category category = Category.builder()
                .id(1L)
                .name("Meals")
                .build();
        Product product = Product.builder()
                .id(4L)
                .name("Lasagna")
                .description("Cheese")
                .category(category)
                .build();
        when(productRepository.findById(4L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(4L);

        assertThat(result.getId()).isEqualTo(4L);
        assertThat(result.getCategoryName()).isEqualTo("Meals");
    }

    @Test
    void createProduct_persistsEntity() {
        Category category = Category.builder()
                .id(2L)
                .name("Desserts")
                .build();
        ProductRequest request = ProductRequest.builder()
                .name("Ice cream")
                .description("Strawberry")
                .categoryId(2L)
                .build();
        Product saved = Product.builder()
                .id(8L)
                .name("Ice cream")
                .description("Strawberry")
                .category(category)
                .build();
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(productRepository.save(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(saved);

        ProductDTO result = productService.createProduct(request);

        assertThat(result.getId()).isEqualTo(8L);
        assertThat(result.getCategoryName()).isEqualTo("Desserts");
    }

    @Test
    void updateProduct_updatesEntity() {
        Category category = Category.builder()
                .id(5L)
                .name("Snacks")
                .build();
        Product product = Product.builder()
                .id(6L)
                .name("Old")
                .description("Old")
                .category(category)
                .build();
        ProductRequest request = ProductRequest.builder()
                .name("New")
                .description("New desc")
                .categoryId(5L)
                .build();
        when(productRepository.findById(6L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(5L)).thenReturn(Optional.of(category));
        when(productRepository.save(product)).thenReturn(product);

        ProductDTO result = productService.updateProduct(6L, request);

        assertThat(result.getName()).isEqualTo("New");
        assertThat(result.getDescription()).isEqualTo("New desc");
    }

    @Test
    void deleteProduct_throwsWhenMissing() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteProduct_deletesWhenPresent() {
        when(productRepository.existsById(12L)).thenReturn(true);

        productService.deleteProduct(12L);

        verify(productRepository).deleteById(12L);
    }
}
