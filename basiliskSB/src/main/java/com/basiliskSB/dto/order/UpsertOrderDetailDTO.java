package com.basiliskSB.dto.order;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data order detail di dalam form yang digunakan untuk insert dan update.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpsertOrderDetailDTO {

	@Schema(description = "Order detail ID PK.")
	private Long id;

	@Schema(description = "Invoice Number FK.")
	private String invoiceNumber;

	@Schema(description = "Product ID FK.")
	@NotNull(message="Product is required.")
	private Long productId;

	@Schema(description = "Jumlah product yang dibeli.")
	@NotNull(message="Customer is required.")
	@Min(value=0, message="Quantity cannot filled with negative value.")
	@Max(value=9999, message="Quantity cannot filled with value of more than 9999.")
	private Integer quantity;

	@Schema(description = "Persentase dalam bilangan desimal dengan 2 angka dibelakang koma, maximum 100.00.")
	@NotNull(message="Discount is required.")
	@Digits(integer=3, fraction=2, message="Must be a decimal value with 2 decimal points.")
	@Min(value=0, message="Discount cannot filled with negative value.")
	@Max(value=100, message="Discount cannot filled with value of more than 100.")
	private Double discount;

	public UpsertOrderDetailDTO(Object row) {
		this.id = MapperHelper.getLongField(row, "id");
		this.invoiceNumber = MapperHelper.getStringField(row, "invoiceNumber");;
		this.productId = MapperHelper.getLongField(row, "productId");
		this.quantity = MapperHelper.getIntegerField(row, "quantity");
		this.discount = MapperHelper.getDoubleField(row, "discount");
	}
}
