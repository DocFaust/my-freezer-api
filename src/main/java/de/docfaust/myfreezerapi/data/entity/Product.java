package de.docfaust.myfreezerapi.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entity representing a product stored in the freezer.
 */
@Entity
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    /**
     * Primary key identifier for the product.
     * Null before persist; assigned by the database identity generator.
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

    /**
     * Timestamp when the product was created.
     * Automatically set by JPA Auditing on entity creation.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the product was last modified.
     * Automatically updated by JPA Auditing on entity modification.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
