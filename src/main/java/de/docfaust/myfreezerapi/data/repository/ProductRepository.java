package de.docfaust.myfreezerapi.data.repository;

import de.docfaust.myfreezerapi.data.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for performing CRUD operations on {@link Product} entities.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
