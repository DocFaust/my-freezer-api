package de.docfaust.myfreezerapi.data.repository;

import de.docfaust.myfreezerapi.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for performing CRUD operations on {@link Category} entities.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
