package de.docfaust.myfreezerapi.dto;

import de.docfaust.myfreezerapi.data.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @NotNull
    private Long id;
    @NotNull
    private String name;

    public static CategoryDTO fromEntity(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
