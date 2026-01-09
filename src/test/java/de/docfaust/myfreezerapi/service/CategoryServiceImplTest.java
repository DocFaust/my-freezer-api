package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.dto.CategoryRequest;
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

    @Test
    void getCategoryById_returnsDto() {
        Category category = Category.builder()
                .id(6L)
                .name("Seafood")
                .build();
        when(categoryRepository.findById(6L)).thenReturn(Optional.of(category));

        CategoryDTO result = categoryService.getCategoryById(6L);

        assertThat(result.getId()).isEqualTo(6L);
        assertThat(result.getName()).isEqualTo("Seafood");
    }

    @Test
    void createCategory_persistsEntity() {
        CategoryRequest request = CategoryRequest.builder()
                .name("Meals")
                .build();
        Category saved = Category.builder()
                .id(9L)
                .name("Meals")
                .build();
        when(categoryRepository.save(org.mockito.ArgumentMatchers.any(Category.class))).thenReturn(saved);

        CategoryDTO result = categoryService.createCategory(request);

        assertThat(result.getId()).isEqualTo(9L);
        assertThat(result.getName()).isEqualTo("Meals");
    }

    @Test
    void updateCategory_updatesEntity() {
        Category category = Category.builder()
                .id(4L)
                .name("Old")
                .build();
        CategoryRequest request = CategoryRequest.builder()
                .name("New")
                .build();
        when(categoryRepository.findById(4L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryDTO result = categoryService.updateCategory(4L, request);

        assertThat(result.getName()).isEqualTo("New");
    }

    @Test
    void deleteCategory_throwsWhenMissing() {
        when(categoryRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.deleteCategory(99L))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteCategory_deletesWhenPresent() {
        when(categoryRepository.existsById(12L)).thenReturn(true);

        categoryService.deleteCategory(12L);

        verify(categoryRepository).deleteById(12L);
    }
}
