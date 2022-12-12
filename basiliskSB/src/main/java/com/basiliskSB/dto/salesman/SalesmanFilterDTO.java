package com.basiliskSB.dto.salesman;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesmanFilterDTO {
    private String employeeNumber;
    private String name;
    private String employeeLevel;
    private String superiorName;
}
