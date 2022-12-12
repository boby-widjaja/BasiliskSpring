package com.basiliskSB.dto.category;
import javax.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.validation.UniqueCategoryName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data form Category yang digunakan untuk insert dan update.")
@NoArgsConstructor
@AllArgsConstructor
@UniqueCategoryName(message="Category with this name is already exist.", idField = "id", nameField ="name")
@Getter
@Setter
public class UpsertCategoryDTO {

	@Schema(description = "PK Category.")
	private Long id;

	@Schema(description = "Nama category maximum 50 characters.")
	@NotBlank(message="Category name is required")
	@Size(max=50, message="Category name can't be more than 50 characters.")
	private String name;

	@Schema(description = "Description maximum 500 characters.")
	@Size(max=500, message="Category description can't be more than 500 characters.")
	private String description;

	public UpsertCategoryDTO(Object entity) {
		this.id = MapperHelper.getLongField(entity, "id");
		this.name = MapperHelper.getStringField(entity, "name");
		this.description = MapperHelper.getStringField(entity, "description");
	}
}
