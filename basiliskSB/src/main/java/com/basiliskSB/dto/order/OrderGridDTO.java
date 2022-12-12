package com.basiliskSB.dto.order;
import java.time.LocalDate;

import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transaksi order yang akan ditampilkan di halaman index grid.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderGridDTO {
	@Schema(description = "Nomor invoice PK.")
	private String invoiceNumber;

	@Schema(description = "Nama perusahaan customer.")
	private String customer;

	@Schema(description = "Nama lengkap salesman.")
	private String salesman;

	@Schema(description = "Tanggal pemesanan order.")
	private LocalDate orderDate;

	@Schema(description = "Nama perusahaan pengiriman")
	private String delivery;

	public OrderGridDTO(Object row) {
		this.invoiceNumber = MapperHelper.getStringField(row, 0);
		this.customer = MapperHelper.getStringField(row, 1);
		this.salesman = MapperHelper.getStringField(row, 2);
		this.orderDate = MapperHelper.getLocalDateField(row, 3);
		this.delivery = MapperHelper.getStringField(row, 4);
	}
}
