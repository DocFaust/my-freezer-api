package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.repository.CategoryRepository;
import de.docfaust.myfreezerapi.data.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
