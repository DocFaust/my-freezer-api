package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.dto.CategoryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link CategoryServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    /**
     * Mock repository used by the service under test.
     */
    @Mock
    private CategoryRepository categoryRepository;

    /**
     * Service under test with injected mocks.
     */
    @InjectMocks
    private CategoryServiceImpl categoryService;

    /**
     * Verifies that entities are mapped to DTOs when fetching categories.
     */
    @Test
    void getAllCategories_mapsEntitiesToDtos() {
        Category category = Category.builder()
                .id(3L)
                .name("Desserts")
                .build();
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(3L);
                    assertThat(dto.getName()).isEqualTo("Desserts");
                });
    }
}
