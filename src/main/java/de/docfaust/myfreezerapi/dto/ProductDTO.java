package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO representing product data returned by the API.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product representation returned by the API")
public class ProductDTO {
    /**
     * Identifier for the product.
     */
    @Schema(description = "Product identifier", example = "1")
    private Long id;
    /**
     * Display name for the product.
     */
    @NotNull
    @Schema(description = "Display name", example = "Peas")
    private String name;
    /**
     * Optional description of the product.
     */
    @Schema(description = "Optional description", example = "Frozen garden peas")
    private String description;
    /**
     * Optional name of the category the product belongs to.
     * May be null if the product has no category assigned.
     */
    @Schema(description = "Name of the related category", example = "Frozen Veggies")
    private String categoryName;
    /**
     * Timestamp when the product was created.
     */
    private LocalDateTime createdAt;
    /**
     * Timestamp when the product was last modified.
     */
    private LocalDateTime updatedAt;

    /**
     * Builds a {@link ProductDTO} from a {@link Product} entity.
     *
     * @param product product entity to convert
     * @return DTO populated from the entity
     * @throws IllegalArgumentException if product is null
     */
    public static ProductDTO fromEntity(final Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
