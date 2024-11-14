package com.example.softtest2.integration;

import com.example.softtest2.entity.AnimalOrderEntity;
import com.example.softtest2.model.OrderStatus;
import com.example.softtest2.repository.AnimalOrderRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql(value = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = "/rollback-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalOrderRepo animalOrderRepo;

    @Test
    public void successfulOrderTest() throws Exception {
        List<AnimalOrderEntity> animalOrderEntities = animalOrderRepo.findAll();
        assertEquals(animalOrderEntities.size(), 1);

        mockMvc.perform(post("/order/quickorder")
                .param("animalId", "1")
                .param("quantity", "10"))
                .andExpect(status().isOk());

        animalOrderEntities = animalOrderRepo.findAll();
        assertEquals(animalOrderEntities.size(), 2);

    }

    @Test
    public void updateOrderUpdatesOrder() throws Exception {

        AnimalOrderEntity order = animalOrderRepo.findById(1L).orElseThrow();
        assertEquals(1, order.getAnimalId());
        assertEquals(10, order.getQuantity());
        assertEquals(OrderStatus.IN_PROGRESS, order.getStatus());


        mockMvc.perform(put("/order/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"animalId\": 2, \"quantity\": 100, \"status\": \"NEW\"}"))
                .andExpect(status().isNoContent());
                /*.andExpect(jsonPath("[0].animalId").value(2))
                .andExpect(jsonPath("[0].quantity").value(100))
                .andExpect(jsonPath("[0].status").value("NEW"));*/

        AnimalOrderEntity updatedOrder = animalOrderRepo.findById(1L).orElseThrow();
        assertEquals(2, updatedOrder.getAnimalId());
        assertEquals(100, updatedOrder.getQuantity());
        assertEquals(OrderStatus.NEW, updatedOrder.getStatus());
    }


    @Test
    public void successfulOrderDelete() throws Exception {
        List<AnimalOrderEntity> animalOrderEntities = animalOrderRepo.findAll();
        assertEquals(animalOrderEntities.size(), 1);

        mockMvc.perform(delete("/order/delete/1"))
                .andExpect(status().isOk());

        animalOrderEntities = animalOrderRepo.findAll();
        assertEquals(animalOrderEntities.size(), 0);

    }




}
