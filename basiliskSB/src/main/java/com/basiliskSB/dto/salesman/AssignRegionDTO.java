package com.basiliskSB.dto.salesman;
import javax.validation.constraints.NotNull;
import com.basiliskSB.validation.UniqueAssignRegionSalesman;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data di dalam form yang digunakan untuk membuat relation antara salesman dan region.")
@NoArgsConstructor
@AllArgsConstructor
@UniqueAssignRegionSalesman(message="This salesman already work in this region.", 
	salesmanEmployeeNumberField = "salesmanEmployeeNumber", regionIdField ="regionId")
@Getter
@Setter
public class AssignRegionDTO {

	@Schema(description = "Salesman Employee Number FK.")
	private String salesmanEmployeeNumber;

	@Schema(description = "Region ID FK.")
	@NotNull(message="Region is required")
	private Long regionId;
}