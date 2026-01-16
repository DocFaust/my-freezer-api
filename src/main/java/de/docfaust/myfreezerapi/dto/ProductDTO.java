package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Product;
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
public class ProductDTO {
    /**
     * Identifier for the product.
     */
    private Long id;
    /**
     * Display name for the product.
     */
    @NotNull
    private String name;
    /**
     * Optional description of the product.
     */
    private String description;
    /**
     * Optional name of the category the product belongs to.
     * May be null if the product has no category assigned.
     */
    private String categoryName;

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
                .build();
    }
}
