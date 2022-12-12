package com.basiliskSB.dto.product;
import javax.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "Data product di dalam form yang digunakan untuk insert dan update.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpsertProductDTO {

	@Schema(description = "Product PK.")
	private Long id;

	@Schema(description = "Nama product, maximum 50 characters.")
	@NotBlank(message="Product name is required.")
	@Size(max=200, message="Product name can't be no more than 50 characters.")
	private String name;

	@Schema(description = "Supplier ID FK")
	private Long supplierId;

	@Schema(description = "Category ID FK")
	@NotNull(message="Category is required.")
	private Long categoryId;

	@Schema(description = "Deskripsi product, maximum 1000 characters.")
	@Size(max=1000, message="Description must can't be no more than 1000 characters.")
	private String description;

	@Schema(description = "Harga product ditulis dalam angka desimal 2.")
	@NotNull(message="Unit price is required.")
	@Digits(integer=12, fraction=2, message="Must be a decimal value with 2 decimal points.")
	private Double price;

	@Schema(description = "Jumlah stock dalam bilangan bulat, maximum 9999.")
	@NotNull(message="Stock is required.")
	@Min(value=0, message="Stock cannot filled with negative value.")
	@Max(value=200, message="Stock cannot filled with value of more than 9999.")
	private Integer stock;

	@Schema(description = "Jumlah stock yang akan di order lagi dari supplier, maximum 9999.")
	@NotNull(message="Incoming order is required.")
	@Min(value=0, message="Order cannot filled with negative value.")
	@Max(value=9999, message="Order cannot filled with value of more than 9999.")
	private Integer onOrder;

	@Schema(description = "Apakah product ini masih di supply oleh perusahaan supplier.")
	@NotNull(message="Discontinue is required.")
	private Boolean discontinue;

	@Schema(description = "File foto product")
	private MultipartFile image;

	@Schema(description = "Path dari image file")
	private String imagePath;

	public UpsertProductDTO(Object entity) {
		this.id = MapperHelper.getLongField(entity, "id");
		this.name = MapperHelper.getStringField(entity, "name");
		this.supplierId = MapperHelper.getLongField(entity, "supplierId");
		this.categoryId = MapperHelper.getLongField(entity, "categoryId");
		this.description = MapperHelper.getStringField(entity, "description");
		this.price = MapperHelper.getDoubleField(entity, "price");
		this.stock = MapperHelper.getIntegerField(entity, "stock");
		this.onOrder = MapperHelper.getIntegerField(entity, "onOrder");
		this.discontinue = MapperHelper.getBooleanField(entity, "discontinue");
		this.imagePath = MapperHelper.getStringField(entity, "imagePath");
	}
}
