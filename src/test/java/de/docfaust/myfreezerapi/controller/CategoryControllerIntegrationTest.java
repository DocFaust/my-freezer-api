package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class CategoryControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void getAllCategoriesReturnsPersistedCategories() throws Exception {
        Category savedCategory = categoryRepository.save(Category.builder()
                .name("Frozen Veggies")
                .build());

        mockMvc.perform(get("/api/v1/categories/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(savedCategory.getId()))
                .andExpect(jsonPath("$[0].name").value("Frozen Veggies"));
    }

    @Test
    void getCategoryByIdReturnsCategory() throws Exception {
        Category savedCategory = categoryRepository.save(Category.builder()
                .name("Seafood")
                .build());

        mockMvc.perform(get("/api/v1/categories/{id}", savedCategory.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedCategory.getId()))
                .andExpect(jsonPath("$.name").value("Seafood"));
    }

    @Test
    void createCategoryPersistsCategory() throws Exception {
        String request = """
                {
                  "name": "Snacks"
                }
                """;

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Snacks"));
    }

    @Test
    void updateCategoryPersistsChanges() throws Exception {
        Category savedCategory = categoryRepository.save(Category.builder()
                .name("Old")
                .build());
        String request = """
                {
                  "name": "New"
                }
                """;

        mockMvc.perform(put("/api/v1/categories/{id}", savedCategory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"));
    }

    @Test
    void deleteCategoryRemovesEntity() throws Exception {
        Category savedCategory = categoryRepository.save(Category.builder()
                .name("To Remove")
                .build());

        mockMvc.perform(delete("/api/v1/categories/{id}", savedCategory.getId()))
                .andExpect(status().isNoContent());
    }
}
