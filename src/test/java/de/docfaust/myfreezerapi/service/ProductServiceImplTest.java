package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.entity.Product;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import de.docfaust.myfreezerapi.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

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
}
