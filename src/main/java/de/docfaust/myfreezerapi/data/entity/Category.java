package de.docfaust.myfreezerapi.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * Entity representing a product category.
 */
@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * Primary key identifier for the category.
     * Null before persist; assigned by the database identity generator.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    /**
     * Display name for the category.
     */
    @NotNull
    private String name;

    /**
     * Products that belong to this category.
     */
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Product> products;
}
