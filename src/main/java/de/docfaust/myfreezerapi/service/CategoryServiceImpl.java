package de.docfaust.myfreezerapi.service;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.dto.CategoryDTO;
import de.docfaust.myfreezerapi.dto.CategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Default {@link CategoryService} implementation backed by a repository.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    /**
     * Repository used to load category data.
     */
    private final CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryDTO> getAllCategories() {
        log.debug("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryDTO::fromEntity).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryDTO getCategoryById(final Long id) {
        log.debug("Fetching category {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Category {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
                });
        return CategoryDTO.fromEntity(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CategoryDTO createCategory(final CategoryRequest request) {
        log.info("Creating category {}", request.getName());
        Category category = Category.builder()
                .name(request.getName())
                .build();
        Category saved = categoryRepository.save(category);
        return CategoryDTO.fromEntity(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CategoryDTO updateCategory(final Long id, final CategoryRequest request) {
        log.info("Updating category {}", id);
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Category {} not found for update", id);
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
                    });
            category.setName(request.getName());
            Category saved = categoryRepository.save(category);
            return CategoryDTO.fromEntity(saved);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Optimistic locking failure when updating category {}", id);
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category was modified by another user. Please refresh and try again."
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteCategory(final Long id) {
        log.info("Deleting category {}", id);
        // Use deleteById directly - it's atomic and will throw exception if not found
        // This eliminates the race condition between existsById and deleteById
        try {
            categoryRepository.deleteById(id);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            log.warn("Category {} not found for deletion", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }
}
