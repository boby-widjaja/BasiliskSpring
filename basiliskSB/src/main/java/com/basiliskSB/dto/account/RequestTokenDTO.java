package com.basiliskSB.dto.account;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "Data object yang digunakan untuk me-request JWT.")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RequestTokenDTO {

    @Schema(description = "Username maximum 20 characters.")
    private String username;

    @Schema(description = "Password maximum 20 characters.")
    private String password;

    @Schema(description = "Username, Email atau topic dari requester.")
    private String subject;

    @Schema(description = "Secret key dari API")
    private String secretKey;

    @Schema(description = "Pengguna API")
    private String audience;
}
