package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

        List<CategoryDTO> result = categoryController.getAllCategories();

        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(dto -> {
                    assertThat(dto.getId()).isEqualTo(7L);
                    assertThat(dto.getName()).isEqualTo("Fruit");
                });
    }
}
