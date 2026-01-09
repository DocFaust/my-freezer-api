package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.dto.CategoryRequest;
import de.docfaust.myfreezerapi.service.CategoryService;
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
 * Unit tests for {@link CategoryControllerV1}.
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerV1Test {

    /**
     * Mocked service dependency for the controller.
     */
    @Mock
    private CategoryService categoryService;

    /**
     * Controller under test with injected mocks.
     */
    @InjectMocks
    private CategoryControllerV1 categoryController;

    /**
     * Verifies that the controller returns category DTOs from the service.
     */
    @Test
    void getAllCategories_returnsDtosFromService() {
        CategoryDTO category = CategoryDTO.builder()
                .id(7L)
                .name("Fruit")
                .build();
        when(categoryService.getAllCategories()).thenReturn(List.of(category));

        ResponseEntity<List<CategoryDTO>> result = categoryController.getAllCategories();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).containsExactly(category);
    }

    @Test
    void getCategoryById_returnsDto() {
        CategoryDTO category = CategoryDTO.builder()
                .id(13L)
                .name("Frozen Veggies")
                .build();
        when(categoryService.getCategoryById(13L)).thenReturn(category);

        ResponseEntity<CategoryDTO> result = categoryController.getCategoryById(13L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(category);
    }

    @Test
    void createCategory_returnsCreatedDto() {
        CategoryRequest request = CategoryRequest.builder()
                .name("Snacks")
                .build();
        CategoryDTO created = CategoryDTO.builder()
                .id(4L)
                .name("Snacks")
                .build();
        when(categoryService.createCategory(request)).thenReturn(created);

        ResponseEntity<CategoryDTO> result = categoryController.createCategory(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(created);
    }

    @Test
    void updateCategory_returnsUpdatedDto() {
        CategoryRequest request = CategoryRequest.builder()
                .name("Meals")
                .build();
        CategoryDTO updated = CategoryDTO.builder()
                .id(8L)
                .name("Meals")
                .build();
        when(categoryService.updateCategory(8L, request)).thenReturn(updated);

        ResponseEntity<CategoryDTO> result = categoryController.updateCategory(8L, request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(updated);
    }

    @Test
    void deleteCategory_returnsNoContent() {
        ResponseEntity<Void> result = categoryController.deleteCategory(6L);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(categoryService).deleteCategory(6L);
    }
}
