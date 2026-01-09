package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
}
