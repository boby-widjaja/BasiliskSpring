package com.basiliskSB.dto.category;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data Category yang untuk ditampilkan di table.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryGridDTO{
	@Schema(description = "PK Category.")
	private Long id;

	@Schema(description = "Nama category product.")
	private String name;

	@Schema(description = "Deskripsi dari category.")
	private String description;

	public CategoryGridDTO(Object row){
		this.id = MapperHelper.getLongField(row, 0);
		this.name = MapperHelper.getStringField(row, 1);
		this.description = MapperHelper.getStringField(row, 2);
	}
}