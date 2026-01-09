package de.docfaust.myfreezerapi.dto;

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
}
