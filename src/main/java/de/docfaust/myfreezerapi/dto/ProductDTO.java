package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Product;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String categoryName;

    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
