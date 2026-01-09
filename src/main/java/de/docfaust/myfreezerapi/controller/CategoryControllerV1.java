package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for category resources.
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryControllerV1 {

    /**
     * Service used for category operations.
     */
    private final CategoryService categoryService;

    /**
     * Returns all categories as DTOs.
     *
     * @return list of category DTOs
     */
    @GetMapping("/")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
