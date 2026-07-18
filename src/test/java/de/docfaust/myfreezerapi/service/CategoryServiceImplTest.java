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
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
    void getCategoryById_throwsWhenMissing() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getCategoryById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(rse.getReason()).isEqualTo("Category not found");
                });
    }

    @Test
    void updateCategory_updatesEntity() {
        Category category = Category.builder()
                .id(4L)
                .name("Old")
                .version(1L)
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
    void updateCategory_throwsWhenMissing() {
        CategoryRequest request = CategoryRequest.builder()
                .name("New")
                .build();
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.updateCategory(99L, request))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(rse.getReason()).isEqualTo("Category not found");
                });
    }

    @Test
    void updateCategory_throwsConflictOnOptimisticLockingFailure() {
        Category category = Category.builder()
                .id(4L)
                .name("Old")
                .version(1L)
                .build();
        CategoryRequest request = CategoryRequest.builder()
                .name("New")
                .build();
        when(categoryRepository.findById(4L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category))
                .thenThrow(new ObjectOptimisticLockingFailureException(Category.class, 4L));

        assertThatThrownBy(() -> categoryService.updateCategory(4L, request))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.CONFLICT);
                    assertThat(rse.getReason()).contains("modified by another user");
                });
    }

    @Test
    void deleteCategory_throwsWhenMissing() {
        org.springframework.dao.EmptyResultDataAccessException exception =
                new org.springframework.dao.EmptyResultDataAccessException(1);
        org.mockito.Mockito.doThrow(exception).when(categoryRepository).deleteById(99L);

        assertThatThrownBy(() -> categoryService.deleteCategory(99L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException rse = (ResponseStatusException) ex;
                    assertThat(rse.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NOT_FOUND);
                });
    }

    @Test
    void deleteCategory_deletesWhenPresent() {
        categoryService.deleteCategory(12L);

        verify(categoryRepository).deleteById(12L);
    }
}
