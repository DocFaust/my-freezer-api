package de.docfaust.myfreezerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Request payload for creating or updating categories.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating or updating a category")
public class CategoryRequest {
    /**
     * Display name for the category.
     */
    @NotBlank
    @Schema(description = "Display name", example = "Frozen Veggies", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
}
