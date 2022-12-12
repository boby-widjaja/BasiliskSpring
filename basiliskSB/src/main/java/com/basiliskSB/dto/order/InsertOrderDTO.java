package com.basiliskSB.dto.order;
import java.time.LocalDate;
import javax.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import org.springframework.format.annotation.DateTimeFormat;
import com.basiliskSB.validation.After;
import com.basiliskSB.validation.UniqueInvoiceNumber;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data yang digunakan untuk menambahkan order baru.")
@NoArgsConstructor
@AllArgsConstructor
@After(message="You can't possibly ship the order before ordering.", 
	previousDateField="orderDate", subsequentDateField="shippedDate")
@After(message="You due date estimation is impossible, it happened before ship date.", 
	previousDateField="shippedDate", subsequentDateField="dueDate")
@Getter
@Setter
public class InsertOrderDTO {

	@Schema(description = "PK Nomor invoice, maximum 20 characters.")
	@NotBlank(message="Product name is required.")
	@Size(max=20, message="Invoice number can't be more than 20 characters.")
	@UniqueInvoiceNumber(message="Invoice numer already existed in the database.")
	private String invoiceNumber;

	@Schema(description = "Customer ID FK.")
	@NotNull(message="Customer is required.")
	private Long customerId;

	@Schema(description = "Salesman Employee Number FK.")
	@NotBlank(message="Sales is required.")
	private String salesEmployeeNumber;

	@Schema(description = "Tanggal pemesanan dalam format yyyy-MM-dd.")
	@NotNull(message="Order date is required.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate orderDate;

	@Schema(description = "Tanggal pengiriman dalam format yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate shippedDate;

	@Schema(description = "Tanggal sampai dalam format yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;

	@Schema(description = "Delivery ID FK")
	@NotNull(message="Delivery company is required.")
	private Long deliveryId;

	@Schema(description = "Alamat tujuan pengiriman, maximum 200 characters.")
	@NotBlank(message="Destination address is required.")
	@Size(max=200, message="Destination address can't be more than 200 characters.")
	private String destinationAddress;

	@Schema(description = "Kota tujuan pengiriman, maximum 20 characters.")
	@NotBlank(message="Destination city is required.")
	@Size(max=20, message="Destination city can't be more than 20 characters.")
	private String destinationCity;

	@Schema(description = "Kode Pos tujuan pengiriman, maximum 5 characters.")
	@NotBlank(message="Destination postal code is required.")
	@Size(max=5, message="Destination postal code can't be more than 5 characters.")
	private String destinationPostalCode;

	public InsertOrderDTO(Object entity) {
		this.invoiceNumber = MapperHelper.getStringField(entity, "invoiceNumber");
		this.customerId = MapperHelper.getLongField(entity, "customerId");
		this.salesEmployeeNumber = MapperHelper.getStringField(entity, "salesEmployeeNumber");
		this.orderDate = MapperHelper.getLocalDateField(entity, "orderDate");
		this.shippedDate = MapperHelper.getLocalDateField(entity, "shippedDate");
		this.dueDate = MapperHelper.getLocalDateField(entity, "dueDate");
		this.deliveryId = MapperHelper.getLongField(entity, "deliveryId");
		this.destinationAddress = MapperHelper.getStringField(entity, "destinationAddress");
		this.destinationCity = MapperHelper.getStringField(entity, "destinationCity");
		this.destinationPostalCode = MapperHelper.getStringField(entity, "postalCode");
	}
}
