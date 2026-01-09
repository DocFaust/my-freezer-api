package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO representing category data returned by the API.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    /**
     * Identifier for the category.
     */
    @NotNull
    private Long id;
    /**
     * Display name for the category.
     */
    @NotNull
    private String name;

    /**
     * Builds a {@link CategoryDTO} from a {@link Category} entity.
     *
     * @param category category entity to convert
     * @return DTO populated from the entity
     */
    public static CategoryDTO fromEntity(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
