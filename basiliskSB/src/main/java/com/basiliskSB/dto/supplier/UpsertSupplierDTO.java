package com.basiliskSB.dto.supplier;
import javax.validation.constraints.*;
import com.basiliskSB.utility.MapperHelper;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data supplier di dalam form yang digunakan untuk insert atau update.")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class UpsertSupplierDTO {
	@Schema(description = "Supplier ID PK.")
	private Long id;

	@Schema(description = "Nama perusahaan Supplier, maximum 50 characters.")
	@NotBlank(message="Company name is required.")
	@Size(max=50, message="Company name can't be more than 50 characters.")
	private String companyName;

	@Schema(description = "Nama perwakilan dari supplier, maximum 200 characters.")
	@NotBlank(message="Contact name is required.")
	@Size(max=200, message="Contact's full name can't be more than 200 characters.")
	private String contactPerson;

	@Schema(description = "Nama jabatan dari perwakilan supplier, maximum 50 characters.")
	@NotBlank(message="Job title is required.")
	@Size(max=50, message="Contact's Job Title can't be more than 50 characters.")
	private String jobTitle;

	@Schema(description = "Alamat dari perusahaan Supplier, maximum 500 characters.")
	@Size(max=500, message="Address can't be more than 500 characters.")
	private String address;

	@Schema(description = "Nama kota dari perusahaan Supplier, maximum 100 characters.")
	@Size(max=100, message="Company's city can't be more than 100 characters.")
	private String city;

	@Schema(description = "Nomor telpon perusahaan supplier atau mobile perwakilan supplier, maximum 20 characters.")
	@Size(max=20, message="Company's phone number can't be more than 20 characters.")
	private String phone;

	@Schema(description = "Email dari perusahaan supplier atau perwakilan supplier.")
	@Size(max=50, message="Contact's email can't be more than 50 characters.")
	private String email;

	public UpsertSupplierDTO(Object entity) {
		this.id = MapperHelper.getLongField(entity, "id");
		this.companyName = MapperHelper.getStringField(entity, "companyName");
		this.contactPerson = MapperHelper.getStringField(entity, "contactPerson");
		this.jobTitle = MapperHelper.getStringField(entity, "jobTitle");
		this.address = MapperHelper.getStringField(entity, "address");
		this.city = MapperHelper.getStringField(entity, "city");
		this.phone = MapperHelper.getStringField(entity, "phone");
		this.email = MapperHelper.getStringField(entity, "email");
	}
}
