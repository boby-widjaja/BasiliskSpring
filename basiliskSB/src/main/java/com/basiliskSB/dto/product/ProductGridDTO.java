package com.basiliskSB.dto.product;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data yang akan ditunjukan di product grid di halaman index.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class ProductGridDTO {
	@Schema(description = "Product PK.")
	private Long id;

	@Schema(description = "Nama product, nama product tidak bersifat unik.")
	private String name;

	@Schema(description = "Nama perusahaan supplier.")
	private String supplier;

	@Schema(description = "Nama dari category product")
	private String category;

	@Schema(description = "Harga product dalam rupiah.")
	private Double price;

	@Schema(description = "Path photo dari picture")
	private String imagePath;

	public ProductGridDTO(Object row){
		this.id = MapperHelper.getLongField(row, 0);
		this.name = MapperHelper.getStringField(row, 1);
		this.supplier = MapperHelper.getStringField(row, 2);
		this.category = MapperHelper.getStringField(row, 3);
		this.price = MapperHelper.getDoubleField(row, 4);
		this.imagePath = MapperHelper.getStringField(row, 5);
	}
}
