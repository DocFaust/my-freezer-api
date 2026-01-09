package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryControllerV1 {

    private final CategoryService categoryService;

    @GetMapping("/")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
