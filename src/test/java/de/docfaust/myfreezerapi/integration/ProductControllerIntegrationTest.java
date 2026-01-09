package de.docfaust.myfreezerapi.integration;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.entity.Product;
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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class ProductControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void getAllProductsReturnsPersistedProducts() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("Seafood")
                .build());

        productRepository.save(Product.builder()
                .name("Salmon")
                .description("Wild caught fillet")
                .category(category)
                .build());

        mockMvc.perform(get("/api/v1/products/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Salmon")))
                .andExpect(content().string(containsString("Seafood")));
    }
}
