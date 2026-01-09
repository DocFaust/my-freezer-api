package de.docfaust.myfreezerapi.controller;

import de.docfaust.myfreezerapi.data.entity.Category;
import de.docfaust.myfreezerapi.data.entity.Product;
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
                .andExpect(jsonPath("$[0].name").value("Salmon"))
                .andExpect(jsonPath("$[0].categoryName").value("Seafood"));
    }

    @Test
    void getProductByIdReturnsProduct() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("Desserts")
                .build());
        Product saved = productRepository.save(Product.builder()
                .name("Ice cream")
                .description("Vanilla")
                .category(category)
                .build());

        mockMvc.perform(get("/api/v1/products/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Ice cream"))
                .andExpect(jsonPath("$.categoryName").value("Desserts"));
    }

    @Test
    void createProductPersistsProduct() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("Meals")
                .build());
        String request = """
                {
                  "name": "Pizza",
                  "description": "Margherita",
                  "categoryId": %d
                }
                """.formatted(category.getId());

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.categoryName").value("Meals"));
    }

    @Test
    void updateProductPersistsChanges() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("Meals")
                .build());
        Product product = productRepository.save(Product.builder()
                .name("Old")
                .description("Old")
                .category(category)
                .build());
        String request = """
                {
                  "name": "New",
                  "description": "New desc",
                  "categoryId": %d
                }
                """.formatted(category.getId());

        mockMvc.perform(put("/api/v1/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New"))
                .andExpect(jsonPath("$.description").value("New desc"));
    }

    @Test
    void deleteProductRemovesEntity() throws Exception {
        Category category = categoryRepository.save(Category.builder()
                .name("Snacks")
                .build());
        Product product = productRepository.save(Product.builder()
                .name("Fries")
                .description("Curly")
                .category(category)
                .build());

        mockMvc.perform(delete("/api/v1/products/{id}", product.getId()))
                .andExpect(status().isNoContent());
    }
}
