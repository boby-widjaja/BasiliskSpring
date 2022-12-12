package com.basiliskSB.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductFilterDTO {
    private String name;
    private Long categoryId;
    private Long supplierId;
}
