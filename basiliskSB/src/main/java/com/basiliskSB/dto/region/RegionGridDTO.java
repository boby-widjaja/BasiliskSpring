package com.basiliskSB.dto.region;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data region yang digunakan untuk ")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegionGridDTO {
	@Schema(description = "Region PK.")
	private Long id;

	@Schema(description = "Nama kota dari region.")
	private String city;

	@Schema(description = "Keterangan dari region ini.")
	private String remark;

	public RegionGridDTO(Object row) {
		this.id = MapperHelper.getLongField(row, 0);
		this.city = MapperHelper.getStringField(row, 1);
		this.remark = MapperHelper.getStringField(row, 2);
	}
}
