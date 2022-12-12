package com.basiliskSB.dto.order;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Ditunjukan di order detail grid di halaman detail.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailGridDTO {

	@Schema(description = "Order Detail PK")
	private Long id;

	@Schema(description = "Nama product yang dibeli di invoice.")
	private String product;

	@Schema(description = "Harga product saat transaksi.")
	private Double price;

	@Schema(description = "Kuantitas product yang dibeli.")
	private Integer quantity;

	@Schema(description = "Persentase diskon untuk product ini.")
	private Double discount;

	@Schema(description = "Harga total dari hasil perhitungan kuantitas, harga dan diskon.")
	private Double totalPrice;

	public OrderDetailGridDTO(Long id, String product, Double price, Integer quantity, Double discount) {
		this.id = id;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
	}

	public OrderDetailGridDTO(Object row, Double totalPrice) {
		this.id = MapperHelper.getLongField(row, 0);
		this.product = MapperHelper.getStringField(row, 1);
		this.price = MapperHelper.getDoubleField(row, 2);
		this.quantity = MapperHelper.getIntegerField(row, 3);
		this.discount = MapperHelper.getDoubleField(row, 4);
		this.totalPrice = totalPrice;
	}
}
