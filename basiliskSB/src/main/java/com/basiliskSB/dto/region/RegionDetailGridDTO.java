package com.basiliskSB.dto.region;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Data salesman yang akan dikeluarkan di grid di halaman index.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegionDetailGridDTO {
    @Schema(description = "Employee Number PK.")
    private String employeeNumber;

    @Schema(description = "Nama lengkap salesman.")
    private String fullName;

    @Schema(description = "Level karyawan.")
    private String level;

    @Schema(description = "Nama lengkap supervisor dari karyawan ini.")
    private String superior;

    public RegionDetailGridDTO(Object row) {
        this.employeeNumber = MapperHelper.getStringField(row, 0);
        this.fullName = MapperHelper.getStringField(row, 1);
        this.level = MapperHelper.getStringField(row, 2);
        this.superior = MapperHelper.getStringField(row, 3);
    }
}
