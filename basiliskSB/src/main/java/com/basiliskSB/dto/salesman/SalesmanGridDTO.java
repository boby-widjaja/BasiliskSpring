package com.basiliskSB.dto.salesman;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data salesman yang akan dikeluarkan di grid di halaman index.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesmanGridDTO {

	@Schema(description = "Employee Number PK.")
	private String employeeNumber;

	@Schema(description = "Nama lengkap salesman.")
	private String fullName;

	@Schema(description = "Level karyawan.")
	private String level;

	@Schema(description = "Nama lengkap supervisor dari karyawan ini.")
	private String superior;

	public SalesmanGridDTO(Object row) {
		this.employeeNumber = MapperHelper.getStringField(row, 0);
		this.fullName = MapperHelper.getStringField(row, 1);
		this.level = MapperHelper.getStringField(row, 2);
		this.superior = MapperHelper.getStringField(row, 3);
	}
}
