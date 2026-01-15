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
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
                .version(1L)
                .build();
        Product product = Product.builder()
                .id(6L)
                .name("Old")
                .description("Old")
                .category(category)
                .version(1L)
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
    void updateProduct_throwsConflictOnOptimisticLockingFailure() {
        Category category = Category.builder()
                .id(5L)
                .name("Snacks")
                .version(1L)
                .build();
        Product product = Product.builder()
                .id(6L)
                .name("Old")
                .description("Old")
                .category(category)
                .version(1L)
                .build();
        ProductRequest request = ProductRequest.builder()
                .name("New")
                .description("New desc")
                .categoryId(5L)
                .build();
        when(productRepository.findById(6L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(5L)).thenReturn(Optional.of(category));
        when(productRepository.save(product))
                .thenThrow(new ObjectOptimisticLockingFailureException(Product.class, 6L));

        assertThatThrownBy(() -> productService.updateProduct(6L, request))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.CONFLICT);
                    assertThat(rse.getReason()).contains("modified by another user");
                });
    }

    @Test
    void deleteProduct_throwsWhenMissing() {
        org.springframework.dao.EmptyResultDataAccessException exception =
                new org.springframework.dao.EmptyResultDataAccessException(1);
        org.mockito.Mockito.doThrow(exception).when(productRepository).deleteById(99L);

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NOT_FOUND);
                });
    }

    @Test
    void deleteProduct_deletesWhenPresent() {
        productService.deleteProduct(12L);

        verify(productRepository).deleteById(12L);
    }

    @Test
    void getAllProducts_mapsEntityWithNullCategory() {
        Product product = Product.builder()
                .id(7L)
                .name("Uncategorized product")
                .description("No category")
                .category(null)
                .build();
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> result = productService.getAllProducts();

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(7L);
                    assertThat(dto.getName()).isEqualTo("Uncategorized product");
                    assertThat(dto.getDescription()).isEqualTo("No category");
                    assertThat(dto.getCategoryName()).isNull();
                });
    }

    @Test
    void getProductById_returnsDtoWithNullCategory() {
        Product product = Product.builder()
                .id(9L)
                .name("Product without category")
                .description("Test")
                .category(null)
                .build();
        when(productRepository.findById(9L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(9L);

        assertThat(result.getId()).isEqualTo(9L);
        assertThat(result.getName()).isEqualTo("Product without category");
        assertThat(result.getCategoryName()).isNull();
    }
}
