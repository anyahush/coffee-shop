package com.mcdonald.coffeeshop.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SupplierInfo {
    private String name;
    private String contact;
}
