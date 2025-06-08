package com.mcdonald.coffeeshop.service;

import com.mcdonald.coffeeshop.model.SupplierInfo;
import com.mcdonald.coffeeshop.model.SupplyItem;
import com.mcdonald.coffeeshop.repository.SupplyItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplyItemServiceTest {

    private SupplyItemRepository repo;
    private SupplyItemService service;

    @BeforeEach
    void setUp() {
        repo = mock(SupplyItemRepository.class);
        service = new SupplyItemService(repo);
    }

    @Test
    void createOrUpdate_savesAndReturnsEntity() {
        var item = new SupplyItem(null, "Beans", "Coffee", 10, 5,
                new SupplierInfo("Best Beans", "contact@beans.com"));
        when(repo.save(item)).thenReturn(item);

        var saved = service.createOrUpdate(item);
        assertThat(saved).isSameAs(item);
        verify(repo).save(item);
    }

    @Test
    void updateQuantity_existingId_updatesAndReturns() {
        var item = new SupplyItem(1L, "Sugar", "Sweetener", 20, 5, null);
        when(repo.findById(1L)).thenReturn(Optional.of(item));
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        var updated = service.updateQuantity(1L, 3);
        assertThat(updated.getQuantityInStock()).isEqualTo(3);
        verify(repo).save(item);
    }

    @Test
    void updateQuantity_missingId_throws() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.updateQuantity(99L, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No SupplyItem found with ID 99");
    }

    @Test
    void getReorderList_filtersCorrectly() {
        var ok   = new SupplyItem(1L, "A", "Cat", 10, 5, null);
        var need = new SupplyItem(2L, "B", "Cat",  4, 5, null);
        var none = new SupplyItem(3L, "C", "Cat",  6, 5, null);
        when(repo.findAll()).thenReturn(of(ok, need, none));

        var list = service.getReorderList();
        assertThat(list).containsExactly(need);
    }

    @Test
    void searchByName_delegatesToRepo() {
        service.searchByName("foo");
        verify(repo).findByNameContainingIgnoreCase("foo");
    }
}
