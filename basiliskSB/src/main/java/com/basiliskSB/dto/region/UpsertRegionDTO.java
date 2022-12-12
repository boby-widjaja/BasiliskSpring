package com.basiliskSB.dto.region;
import javax.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data region di dalam form yang digunakan untuk kebutuhan insert dan update.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpsertRegionDTO {
	@Schema(description = "Region PK.")
	private Long id;

	@Schema(description = "Nama kota dari region.")
	@NotBlank(message="City is required")
	@Size(max=50, message="City can't be more than 50 characters.")
	private String city;

	@Schema(description = "Keterangan dari region.")
	@Size(max=2000, message="Remark description can't be more than 2000 characters.")
	private String remark;

	public UpsertRegionDTO(Object entity) {
		this.id = MapperHelper.getLongField(entity, "id");
		this.city = MapperHelper.getStringField(entity, "city");
		this.remark = MapperHelper.getStringField(entity, "remark");
	}
}
