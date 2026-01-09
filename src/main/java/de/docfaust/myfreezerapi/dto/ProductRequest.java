package de.docfaust.myfreezerapi.dto;

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
public class ProductRequest {
    /**
     * Display name for the product.
     */
    @NotBlank
    private String name;

    /**
     * Optional description of the product.
     */
    private String description;

    /**
     * Identifier of the category that the product belongs to.
     */
    @NotNull
    private Long categoryId;
}
