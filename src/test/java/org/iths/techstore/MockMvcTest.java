package org.iths.techstore;

import org.iths.techstore.Model.Product;
import org.iths.techstore.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private List<Long> productIds = new ArrayList<>();

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();

        Product p1 = productRepository.save(new Product("Sony", "Phone", 3099, 31));
        Product p2 = productRepository.save(new Product("Phillips", "TV", 8999, 43));
        Product p3 = productRepository.save(new Product("iPad", "Tablet", 11999, 98));

        productIds.add(p1.getId());
        productIds.add(p2.getId());
        productIds.add(p3.getId());
    }

    @Test
    void testGetSingleProduct() throws Exception {
        Long idToTest = productIds.get(1);
        mockMvc.perform(get("/products/" + idToTest))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllProducts() throws Exception {

        System.out.println("Number of products: " + productRepository.count());
        assertEquals(3, productRepository.count());
        assertEquals("Sony", productRepository.findAll().get(0).getName());
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateProduct() throws Exception {
        long countBefore = productRepository.count();

        mockMvc.perform(post("/products")
                        .flashAttr("product", new Product("Samsung", "Phone", 5999, 54)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        assertEquals(countBefore + 1, productRepository.count());
    }
}
