package com.basiliskSB.dto.salesman;
import java.time.LocalDate;
import javax.validation.constraints.*;

import com.basiliskSB.utility.MapperHelper;
import org.springframework.format.annotation.DateTimeFormat;
import com.basiliskSB.validation.After;
import com.basiliskSB.validation.FutureParadox;
import com.basiliskSB.validation.UniqueSalesmanNumber;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data salesman yang akan digunakan untuk insert form.")
@NoArgsConstructor
@AllArgsConstructor
@After(message="This salesman can't possibly hired before born.", previousDateField="birthDate", subsequentDateField="hiredDate")
@Getter
@Setter
public class InsertSalesmanDTO {

	@Schema(description = "Employee Number PK, maximum 20 characters.")
	@NotBlank(message="Employee number is required.")
	@Size(max=20, message="Employee number can't be more than 20 characters.")
	@UniqueSalesmanNumber(message="Employee number is already existed.")
	private String employeeNumber;

	@Schema(description = "Nama depan salesman, maximum 50 characters.")
	@NotBlank(message="First name is required.")
	@Size(max=50, message="First name can't be more than 50 characters.")
	private String firstName;

	@Schema(description = "Nama belakang salesman, maximum 50 characters.")
	@Size(max=50, message="Last name can't be more than 50 characters.")
	private String lastName;

	@Schema(description = "Level employee, maximum 50 characters.")
	@NotBlank(message="Level is required.")
	private String level;

	@Schema(description = "Tanggal lahir salesman dalam format yyyy-MM-dd.")
	@NotNull(message="Birth date is required.")
	@FutureParadox(message="This salesman can't be possibly born in the future.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	@Schema(description = "Tanggal mulai bekerja salesman dalam format yyyy-MM-dd.")
	@NotNull(message="Hired date is required.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate hiredDate;

	@Schema(description = "Alamat tempat tinggal salesman, maximum 500 characters.")
	@Size(max=500, message="Address can't be more than 500 characters.")
	private String address;

	@Schema(description = "Nama kota asal dari salesman, maximum 50 characters.")
	@Size(max=50, message="City can't be more than 50 characters.")
	private String city;

	@Schema(description = "Nomor mobile dari salesman, maximum 20 characters.")
	@Size(max=20, message="Phone number can't be more than 20 characters.")
	private String phone;

	@Schema(description = "Superior Employee Number FK.")
	private String superiorEmployeeNumber;

	public InsertSalesmanDTO(Object entity) {
		this.employeeNumber = MapperHelper.getStringField(entity, "employeeNumber");
		this.firstName = MapperHelper.getStringField(entity, "firstName");
		this.lastName = MapperHelper.getStringField(entity, "lastName");
		this.level = MapperHelper.getStringField(entity, "level");
		this.birthDate = MapperHelper.getLocalDateField(entity, "birthDate");
		this.hiredDate = MapperHelper.getLocalDateField(entity, "hiredDate");
		this.address = MapperHelper.getStringField(entity, "address");
		this.city = MapperHelper.getStringField(entity, "city");
		this.phone = MapperHelper.getStringField(entity, "phone");
		this.superiorEmployeeNumber = MapperHelper.getStringField(entity, "superiorEmployeeNumber");
	}
}
