package com.mcdonald.coffeeshop.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SupplyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonUnwrapped(prefix="supplier.")
    private Long id;
    private String name;
    private String category;
    private Integer quantityInStock;
    private Integer reorderLevel;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "supplier_name")),
            @AttributeOverride(name = "contactEmail", column = @Column(name = "supplier_email"))
    })
    private SupplierInfo supplier;
}
