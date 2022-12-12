package com.basiliskSB.dto.customer;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data Customer yang akan ditampilkan di index grid.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerGridDTO {

	@Schema(description = "Customer PK.")
	private Long id;

	@Schema(description = "Nama perusahaan Customer.")
	private String companyName;

	@Schema(description = "Nama seseorang wakil dari perusahaan Customer.")
	private String contactPerson;

	@Schema(description = "Alamat perusahaan customer.")
	private String address;

	@Schema(description = "Lokasi kota dari perusahaan Customer.")
	private String city;

	public CustomerGridDTO(Object row) {
		this.id = MapperHelper.getLongField(row, 0);
		this.companyName = MapperHelper.getStringField(row, 1);
		this.contactPerson = MapperHelper.getStringField(row, 2);
		this.address = MapperHelper.getStringField(row, 3);
		this.city = MapperHelper.getStringField(row, 4);
	}
}
