package com.basiliskSB.dto.salesman;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Data region yang digunakan untuk ")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalesmanDetailGridDTO {
    @Schema(description = "Region PK.")
    private Long id;

    @Schema(description = "Nama kota dari region.")
    private String city;

    @Schema(description = "Keterangan dari region ini.")
    private String remark;

    public SalesmanDetailGridDTO(Object row) {
        this.id = MapperHelper.getLongField(row, 0);
        this.city = MapperHelper.getStringField(row, 1);
        this.remark = MapperHelper.getStringField(row, 2);
    }
}
