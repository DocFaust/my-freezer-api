package de.docfaust.myfreezerapi.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Entity representing a product stored in the freezer.
 */
@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /**
     * Primary key identifier for the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Category that the product belongs to.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    /**
     * Version field for optimistic locking.
     * Automatically incremented on each update to detect concurrent modifications.
     */
    @Version
    private Long version;
}
