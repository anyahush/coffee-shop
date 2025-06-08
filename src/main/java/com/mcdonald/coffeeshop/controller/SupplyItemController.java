package com.mcdonald.coffeeshop.controller;
import com.mcdonald.coffeeshop.model.SupplyItem;
import com.mcdonald.coffeeshop.service.SupplyItemService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyItemController {
    private final SupplyItemService service;
    // 1. CREATE
    @PostMapping
    public ResponseEntity<SupplyItem> create(@RequestBody SupplyItem item) {
        SupplyItem saved = service.createOrUpdate(item);
        return ResponseEntity.status(201).body(saved);
    }

    // 2. READ ALL
    @GetMapping
    public List<SupplyItem> listAll() {
        return service.findAll();
    }

    // 3. READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SupplyItem> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. UPDATE (full replace)
    @PutMapping("/{id}")
    public ResponseEntity<SupplyItem> update(
            @PathVariable Long id,
            @RequestBody SupplyItem item) {

        return service.findById(id)
                .map(existing -> {
                    item.setId(id);
                    SupplyItem saved = service.createOrUpdate(item);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 6. SEARCH (by name, category or supplier)
    @GetMapping("/search")
    public List<SupplyItem> search(
            @RequestParam(name="name",     required=false) String name,
            @RequestParam(name="category", required=false) String category,
            @RequestParam(name="supplier", required=false) String supplier
    ) {
        if (name != null) {
            return service.searchByName(name);
        }
        if (category != null) {
            return service.searchByCategory(category);
        }
        if (supplier != null) {
            return service.searchBySupplier(supplier);
        }
        return List.of();
    }

    // 7. UPDATE QUANTITY
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<SupplyItem> updateQuantity(
            @PathVariable Long id,
            @RequestBody Map<String,Integer> body) {

        Integer qty = body.get("quantity");
        if (qty == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            SupplyItem updated = service.updateQuantity(id, qty);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // 8. REORDER LIST
// GET /api/supplies/reorder
    @GetMapping("/reorder")
    public List<SupplyItem> getReorderList() {
        return service.getReorderList();
    }

    // 9. IMPORT FROM CSV
// POST /api/supplies/import (form‐data file field called "file")
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            MappingIterator<SupplyItem> it = mapper
                    .readerFor(SupplyItem.class)
                    .with(schema)
                    .readValues(file.getInputStream());

            it.forEachRemaining(service::createOrUpdate);
            return ResponseEntity.ok("Imported successfully");
        } catch (IOException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Failed to parse CSV: " + e.getMessage());
        }
    }
}