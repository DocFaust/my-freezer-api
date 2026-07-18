package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
    @NotNull
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
     * Name of the category the product belongs to.
     */
    @NotNull
    @Schema(description = "Name of the related category", example = "Frozen Veggies")
    private String categoryName;

    /**
     * Builds a {@link ProductDTO} from a {@link Product} entity.
     *
     * @param product product entity to convert
     * @return DTO populated from the entity
     */
    public static ProductDTO fromEntity(final Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
