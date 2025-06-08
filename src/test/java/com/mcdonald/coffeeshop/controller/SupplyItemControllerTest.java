package com.mcdonald.coffeeshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcdonald.coffeeshop.model.SupplierInfo;
import com.mcdonald.coffeeshop.model.SupplyItem;
import com.mcdonald.coffeeshop.service.SupplyItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.of;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SupplyItemControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @MockBean SupplyItemService service;

    @TestConfiguration
    static class Config {
        @Bean public ObjectMapper objectMapper() {
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

    @Test @DisplayName("GET /api/supplies/{id} → 200 or 404")
    void getById() throws Exception {
        var found = new SupplyItem(2L, "Milk", "Dairy",5,2,null);
        Mockito.when(service.findById(2L)).thenReturn(Optional.of(found));
        Mockito.when(service.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/supplies/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Dairy"));

        mvc.perform(get("/api/supplies/99"))
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("POST /api/supplies → 201")
    void create() throws Exception {
        var toCreate = new SupplyItem(null,"Sugar","Condiment",10,5,null);
        var created  = new SupplyItem(3L,"Sugar","Condiment",10,5,null);
        Mockito.when(service.createOrUpdate(any())).thenReturn(created);

        mvc.perform(post("/api/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test @DisplayName("PUT /api/supplies/{id} → 200 or 404")
    void update() throws Exception {
        var existing = new SupplyItem(5L,"X","Y",1,1,null);
        var updated  = new SupplyItem(5L,"X2","Y2",2,2,null);

        Mockito.when(service.findById(5L)).thenReturn(Optional.of(existing));
        Mockito.when(service.createOrUpdate(any()))
                .thenReturn(updated);

        mvc.perform(put("/api/supplies/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("X2"));

        // missing
        Mockito.when(service.findById(99L)).thenReturn(Optional.empty());
        mvc.perform(put("/api/supplies/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("DELETE /api/supplies/{id} → 204 or 404")
    void delete() throws Exception {
        Mockito.when(service.findById(7L)).thenReturn(Optional.of(new SupplyItem()));
        mvc.perform(MockMvcRequestBuilders.delete("/api/supplies/7"))
                .andExpect(status().isNoContent());

        Mockito.when(service.findById(99L)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.delete("/api/supplies/99"))
                .andExpect(status().isNotFound());
    }

    @Test @DisplayName("GET /api/supplies/search?name=…")
    void searchByName() throws Exception {
        var a = new SupplyItem(8L,"A","C",1,1,null);
        Mockito.when(service.searchByName("A")).thenReturn(List.of(a));

        mvc.perform(get("/api/supplies/search")
                        .param("name","A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(8));
    }

    @Test @DisplayName("GET /api/supplies/search?category=…")
    void searchByCategory() throws Exception {
        var b = new SupplyItem(9L,"B","Cat",1,1,null);
        Mockito.when(service.searchByCategory("Cat")).thenReturn(List.of(b));

        mvc.perform(get("/api/supplies/search")
                        .param("category","Cat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Cat"));
    }

    @Test @DisplayName("GET /api/supplies/search?supplier=…")
    void searchBySupplier() throws Exception {
        var sup = new SupplierInfo("S","c@mail");
        var c = new SupplyItem(10L,"C","Cat",1,1,sup);
        Mockito.when(service.searchBySupplier("S")).thenReturn(List.of(c));

        mvc.perform(get("/api/supplies/search")
                        .param("supplier","S"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].supplier.name").value("S"));
    }

    @Test @DisplayName("GET /api/supplies/search with no params → empty")
    void searchNoParams() throws Exception {
        mvc.perform(get("/api/supplies/search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test @DisplayName("PATCH /api/supplies/{id}/quantity → 200 or 404")
    void patchQuantity() throws Exception {
        var updated = new SupplyItem(4L,"X","Y",20,10,null);
        Mockito.when(service.updateQuantity(4L,20)).thenReturn(updated);
        Mockito.doThrow(new IllegalArgumentException())
                .when(service).updateQuantity(99L,1);

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

    @Test @DisplayName("GET /api/supplies/reorder → list")
    void reorderList() throws Exception {
        var low = new SupplyItem(5L,"Low","Cat",2,5,null);
        Mockito.when(service.getReorderList()).thenReturn(List.of(low));

        mvc.perform(get("/api/supplies/reorder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(5));
    }

    @Test @DisplayName("POST /api/supplies/import → success")
    void importCsvSuccess() throws Exception {
        String csv = "name,category,quantityInStock,reorderLevel,supplier.name,supplier.contactEmail\n"
                + "Foo,Bar,1,1,S1,e@e\n";
        MockMultipartFile file = new MockMultipartFile(
                "file","sup.csv","text/csv", csv.getBytes(StandardCharsets.UTF_8)
        );

        mvc.perform(multipart("/api/supplies/import")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("Imported successfully"));
    }

    @Test @DisplayName("POST /api/supplies/import → negative → 400")
    void importCsvNegativeFails() throws Exception {
        String csv = "name,category,quantityInStock,reorderLevel,supplier.name,supplier.contactEmail\n"
                + "Foo,Bar,-1,1,S1,e@e\n";
        MockMultipartFile file = new MockMultipartFile(
                "file","sup.csv","text/csv", csv.getBytes(StandardCharsets.UTF_8)
        );

        mvc.perform(multipart("/api/supplies/import")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString("Negative numbers not allowed")
                ));
    }

    @Test
    @DisplayName("POST /api/supplies/import with malformed CSV → 400")
    void importCsvMalformed() throws Exception {
        MockMultipartFile bad = new MockMultipartFile(
                "file",
                "bad.csv",
                "text/csv",
                "not,a,valid,header\nfoo,bar".getBytes()
        );

        mvc.perform(multipart("/api/supplies/import")
                        .file(bad))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(startsWith("Failed to parse CSV")));
    }

}
