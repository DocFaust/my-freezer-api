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
    @NotNull
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
     * Name of the category the product belongs to.
     */
    @NotNull
    private String categoryName;

    /**
     * Builds a {@link ProductDTO} from a {@link Product} entity.
     *
     * @param product product entity to convert
     * @return DTO populated from the entity
     */
    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
