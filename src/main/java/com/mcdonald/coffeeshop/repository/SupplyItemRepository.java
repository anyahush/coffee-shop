package com.mcdonald.coffeeshop.repository;

import com.mcdonald.coffeeshop.model.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data JPA repository for SupplyItem.
 * * Provides: * - basic CRUD (findAll, findById, save, deleteById, etc.)
 * * - plus any custom finder methods you choose to declare below. */
@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
    // Find all items whose name contains the given string (case‐insensitive)
    List<SupplyItem> findByNameContainingIgnoreCase(String name);

    // Find all items in a given category (exact match, ignoring case)
    List<SupplyItem> findByCategoryIgnoreCase(String category);

    // Find all items where the embedded SupplierInfo.name contains the given snippet
    List<SupplyItem> findBySupplierNameContainingIgnoreCase(String supplierName);
}