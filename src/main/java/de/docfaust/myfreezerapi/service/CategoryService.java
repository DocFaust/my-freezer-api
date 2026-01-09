package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.dto.CategoryRequest;

import java.util.List;

/**
 * Service interface for category operations.
 */
public interface CategoryService {
    /**
     * Retrieves all categories.
     *
     * @return list of category DTOs
     */
    List<CategoryDTO> getAllCategories();

    /**
     * Retrieves a single category by identifier.
     *
     * @param id category identifier
     * @return category DTO
     */
    CategoryDTO getCategoryById(Long id);

    /**
     * Creates a new category.
     *
     * @param request category request payload
     * @return created category DTO
     */
    CategoryDTO createCategory(CategoryRequest request);

    /**
     * Updates an existing category.
     *
     * @param id      category identifier
     * @param request category request payload
     * @return updated category DTO
     */
    CategoryDTO updateCategory(Long id, CategoryRequest request);

    /**
     * Deletes a category by identifier.
     *
     * @param id category identifier
     */
    void deleteCategory(Long id);
}
