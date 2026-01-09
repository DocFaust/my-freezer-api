package de.docfaust.myfreezerapi.dto;

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
public class CategoryRequest {
    /**
     * Display name for the category.
     */
    @NotBlank
    private String name;
}
