package de.docfaust.myfreezerapi.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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
@EntityListeners(AuditingEntityListener.class)
public class Category {
    /**
     * Primary key identifier for the category.
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
     * Note: CascadeType.ALL with orphanRemoval ensures that when a category is deleted,
     * all associated products are also deleted. This prevents orphaned products.
     * If you need to prevent category deletion when products exist, implement
     * a business rule check in the service layer instead.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Product> products;

    /**
     * Version field for optimistic locking.
     * Automatically incremented on each update to detect concurrent modifications.
     */
    @Version
    private Long version;

    /**
     * Timestamp when the category was created.
     * Automatically set by JPA Auditing on entity creation.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the category was last modified.
     * Automatically updated by JPA Auditing on entity modification.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
