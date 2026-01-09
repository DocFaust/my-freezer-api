package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.dto.CategoryRequest;
import de.docfaust.myfreezerapi.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for category resources.
 */
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        log.debug("Handling get all categories request");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Returns a single category by identifier.
     *
     * @param id category identifier
     * @return category DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable final Long id) {
        log.debug("Handling get category request {}", id);
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    /**
     * Creates a new category.
     *
     * @param request category request payload
     * @return created category DTO
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody final CategoryRequest request) {
        log.info("Handling create category request");
        CategoryDTO created = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing category.
     *
     * @param id      category identifier
     * @param request category request payload
     * @return updated category DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable final Long id,
            @Valid @RequestBody final CategoryRequest request
    ) {
        log.info("Handling update category request {}", id);
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    /**
     * Deletes a category by identifier.
     *
     * @param id category identifier
     * @return no content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable final Long id) {
        log.info("Handling delete category request {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
