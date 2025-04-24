package com.example.softtest2.integration;

import com.example.softtest2.entity.AnimalEntity;
import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.service.AnimalOrderService;
import com.example.softtest2.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
//import MockMvc static methods
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql("/test-data.sql")
public class AnimalOrderServiceIntegrationTest {

    @Autowired
    private AnimalOrderService animalOrderService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAnimalOrderService() {

        //Add animal to the database
//        AnimalEntity animalEntity = new AnimalEntity();
//        animalEntity.setAnimal("Dog");
//        animalEntity.setQuantity(10);
//        animalService.createAnimal(animalEntity);
        // ↑↑↑
        //Redundant code, as the test-data.sql file already contains the data

        //Create a new order
        animalOrderService.createQuickOrder(1, 10, 1);


        List<AnimalOrderEntity> animalOrderEntities = animalOrderService.getAllAnimalOrders();

        animalOrderEntities.forEach(System.out::println);

    }

    @Test
    public void testAnimalOrderServiceMock() throws Exception {

        this.mockMvc.perform(get("/order/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].animalId").value(1))
                .andExpect(jsonPath("[0].quantity").value(10));

    }

    @Test
    public void createQuickOrderCreatesNewOrder() throws Exception {
        this.mockMvc.perform(post("/order/quickorder")
                .param("animalId", "1")
                .param("quantity", "5"))
                .andExpect(status().isOk());
    } // maybe should add a check for the status of the order

    @Test
    public void createQuickOrderFailsWhenAnimalNotFound() throws Exception {
        this.mockMvc.perform(post("/order/quickorder")
                        .param("animalId", "9999")
                        .param("quantity", "5"))
                        .andExpect(status().isNotFound());
    } // got 400, expected 404 FIXED
    @Test
    public void createQuickOrderFailsWhenQuantityIsZero() throws Exception {
        this.mockMvc.perform(post("/order/quickorder")
                        .param("animalId", "1")
                        .param("quantity", "0"))
                .andExpect(status().isBadRequest());
    } // got 400, expected 400

    @Test
    public void createQuickOrderFailsWhenQuantityIsNegative() throws Exception {
        this.mockMvc.perform(post("/order/quickorder")
                        .param("animalId", "1")
                        .param("quantity", "-5"))
                .andExpect(status().isBadRequest());
        //Expect org.springframework.dao.DataIntegrityViolationException


    }



}
