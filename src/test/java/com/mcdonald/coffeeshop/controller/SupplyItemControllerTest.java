package com.mcdonald.coffeeshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcdonald.coffeeshop.model.SupplyItem;
import com.mcdonald.coffeeshop.service.SupplyItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;
import static java.util.Map.of;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(controllers = SupplyItemController.class)
//@ImportAutoConfiguration(
//        exclude = {
//                SecurityAutoConfiguration.class,
//                OAuth2ClientAutoConfiguration.class
//        }
//)
@SpringBootTest
@AutoConfigureMockMvc
class SupplyItemControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @MockBean SupplyItemService service;

    @TestConfiguration
    static class Config {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }


    @Test @DisplayName("GET /api/supplies returns list")
    void listAll() throws Exception {
        var item = new SupplyItem(1L, "Tea", "Beverage", 8, 4, null);
        Mockito.when(service.findAll()).thenReturn(List.of(item));

        mvc.perform(get("/api/supplies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Tea"));
    }

    @Test @DisplayName("GET /api/supplies/{id} returns 200 or 404")
    void getById() throws Exception {
        var item = new SupplyItem(2L, "Milk", "Dairy", 5, 2, null);
        Mockito.when(service.findById(2L)).thenReturn(Optional.of(item));
        Mockito.when(service.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/supplies/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Dairy"));

        mvc.perform(get("/api/supplies/99"))
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("POST /api/supplies creates entity")
    void create() throws Exception {
        var toCreate = new SupplyItem(null, "Sugar", "Condiment",10,5,null);
        var created  = new SupplyItem(3L, "Sugar","Condiment",10,5,null);
        Mockito.when(service.createOrUpdate(any())).thenReturn(created);

        mvc.perform(post("/api/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test @DisplayName("PATCH /api/supplies/{id}/quantity updates or 404")
    void patchQuantity() throws Exception {
        var updated = new SupplyItem(4L,"X","Y",20,10,null);
        Mockito.when(service.updateQuantity(4L, 20)).thenReturn(updated);
        Mockito.doThrow(new IllegalArgumentException()).when(service).updateQuantity(99L, 1);

        mvc.perform(patch("/api/supplies/4/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(of("quantity",20))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantityInStock").value(20));

        mvc.perform(patch("/api/supplies/99/quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(of("quantity",1))))
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("GET /api/supplies/reorder returns filtered list")
    void reorderList() throws Exception {
        var low = new SupplyItem(5L,"Low","Cat",2,5,null);
        Mockito.when(service.getReorderList()).thenReturn(List.of(low));

        mvc.perform(get("/api/supplies/reorder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5));
    }
}