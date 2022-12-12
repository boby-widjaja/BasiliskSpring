package com.basiliskSB.dto.supplier;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Supplier yang akan ditunjukan di dalam grid di dalam halaman index.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class SupplierGridDTO {

	@Schema(description = "Supplier ID PK")
	private Long id;

	@Schema(description = "Nama perusahaan Supplier.")
	private String companyName;

	@Schema(description = "Nama orang perwakilan dari Supplier.")
	private String contactPerson;

	@Schema(description = "Jabatan dari perwakilan Supplier.")
	private String jobTitle;

	public SupplierGridDTO(Object row) {
		this.id = MapperHelper.getLongField(row, 0);
		this.companyName = MapperHelper.getStringField(row, 1);
		this.contactPerson = MapperHelper.getStringField(row, 2);
		this.jobTitle = MapperHelper.getStringField(row, 3);
	}
}
