package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.dto.CategoryDTO;

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
}
