package com.mcdonald.coffeeshop.service;
import com.mcdonald.coffeeshop.model.SupplyItem;
import com.mcdonald.coffeeshop.repository.SupplyItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List; import java.util.Optional;

/** Service layer for handling business logic around SupplyItem */

@Service
@RequiredArgsConstructor
public class SupplyItemService {
    // Spring will inject the repository (thanks to @RequiredArgsConstructor)
    private final SupplyItemRepository repository;

    // Create a new item or update an existing one. */
    public SupplyItem createOrUpdate(SupplyItem item) {
    // You could add any pre‐save validation here (e.g. negative quantity checks)
        return repository.save(item); }

    /** Find all supply items. */
    public List<SupplyItem> findAll() {
        return repository.findAll();
    }

    /** Find one supply item by its ID. */
    public Optional<SupplyItem> findById(Long id) {
        return repository.findById(id);
    }

    /** Delete one supply item by its ID. */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /** Search by name (partial match, case‐insensitive). */
    public List<SupplyItem> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    /** Search by category (exact match, case‐insensitive). */
    public List<SupplyItem> searchByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    } /** Search by supplier name (partial, case‐insensitive). */

    public List<SupplyItem> searchBySupplier(String supplierName) {
        return repository.findBySupplierNameContainingIgnoreCase(supplierName);
    }

    /** Update only the quantity of a supply item.
     * @param id the item s ID
     * @param newQuantity the new stock quantity
     * @return the updated SupplyItem
     * @throws IllegalArgumentException if no item with that ID exists */
    public SupplyItem updateQuantity(Long id, int newQuantity) {
        SupplyItem item = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No SupplyItem found with ID " + id));
        item.setQuantityInStock(newQuantity); return repository.save(item);
    }

    /**
     * Get a list of all items where quantityInStock <= reorderLevel.
     * Useful for building a reorder now list. */
    public List<SupplyItem> getReorderList() {
        return repository.findAll().stream()
                .filter(si -> si.getQuantityInStock() != null
                        && si.getReorderLevel() != null
                        && si.getQuantityInStock() <= si.getReorderLevel())
                .toList();
    }

}