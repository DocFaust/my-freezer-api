package de.docfaust.myfreezerapi.data.repository;

import de.docfaust.myfreezerapi.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
