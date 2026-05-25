package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for DTO mapping helpers.
 */
class DtoMappingTest {

    @Test
    void categoryDtoFromEntity_throwsWhenNull() {
        assertThatThrownBy(() -> CategoryDTO.fromEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category cannot be null");
    }

    @Test
    void productDtoFromEntity_throwsWhenNull() {
        assertThatThrownBy(() -> ProductDTO.fromEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product cannot be null");
    }
}
