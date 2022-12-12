package com.basiliskSB.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderFilterDTO {
    private String invoiceNumber;
    private Long customerId;
    private String employeeNumber;
    private Long deliveryId;
    private LocalDate orderDate;
}
