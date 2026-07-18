package de.docfaust.myfreezerapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Request payload for creating or updating products.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request payload for creating or updating a product")
public class ProductRequest {
    /**
     * Display name for the product.
     */
    @NotBlank
    @Schema(description = "Display name", example = "Peas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * Optional description of the product.
     */
    @Schema(description = "Optional description", example = "Frozen garden peas")
    private String description;

    /**
     * Identifier of the category that the product belongs to.
     */
    @NotNull
    @Schema(description = "Related category identifier", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;
}
