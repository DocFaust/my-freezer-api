package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Category representation returned by the API")
public class CategoryDTO {
    /**
     * Identifier for the category.
     */
    @NotNull
    @Schema(description = "Category identifier", example = "1")
    private Long id;
    /**
     * Display name for the category.
     */
    @NotNull
    @Schema(description = "Display name", example = "Frozen Veggies")
    private String name;

    /**
     * Builds a {@link CategoryDTO} from a {@link Category} entity.
     *
     * @param category category entity to convert
     * @return DTO populated from the entity
     */
    public static CategoryDTO fromEntity(final Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
