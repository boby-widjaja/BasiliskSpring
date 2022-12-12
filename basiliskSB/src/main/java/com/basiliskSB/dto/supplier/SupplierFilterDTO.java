package com.basiliskSB.dto.supplier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SupplierFilterDTO {
    private String company;
    private String contact;
    private String jobTitle;
}
