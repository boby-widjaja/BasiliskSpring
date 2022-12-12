package com.basiliskSB.dto.account;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.basiliskSB.validation.Compare;
import com.basiliskSB.validation.UniqueUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data Account yang untuk me-register user baru.")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Compare(message="Password is not matched.", firstField="password", secondField="passwordConfirmation")
public class RegisterDTO {
	@Schema(description = "Username maximum 20 characters.")
	@UniqueUsername(message="Username is already exist within the database.")
	@NotBlank(message="Username is required")
	@Size(max=20, message="Username can't be more than 20 characters.")
	private String username;

	@Schema(description = "Password yang digunakan untuk register maximum 20 characters.")
	@NotBlank(message="Password is required")
	@Size(max=20, message="Password can't be more than 20 characters.")
	private String password;

	@Schema(description = "Mengkonfirmasi password baru.")
	@NotBlank(message="Password confirmation is required")
	@Size(max=20, message="Password confirmation can't be more than 20 characters.")
	private String passwordConfirmation;

	@Schema(description = "Pilih role antara Administrator, Salesman, atau Finance.")
	@NotBlank(message="Role is required")
	private String role;
}
