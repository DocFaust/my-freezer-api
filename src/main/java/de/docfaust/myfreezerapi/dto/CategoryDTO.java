package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
    @Schema(description = "Category identifier", example = "1")
    private Long id;
    /**
     * Display name for the category.
     */
    @NotNull
    @Schema(description = "Display name", example = "Frozen Veggies")
    private String name;
    /**
     * Timestamp when the category was created.
     */
    private LocalDateTime createdAt;
    /**
     * Timestamp when the category was last modified.
     */
    private LocalDateTime updatedAt;

    /**
     * Builds a {@link CategoryDTO} from a {@link Category} entity.
     *
     * @param category category entity to convert
     * @return DTO populated from the entity
     * @throws IllegalArgumentException if category is null or required fields are missing
     */
    public static CategoryDTO fromEntity(final Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
