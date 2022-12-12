package com.basiliskSB.dto.utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailGridPageDTO extends GridPageDTO{
    private Object header;

    public DetailGridPageDTO(List<Object> grid, Integer page, Integer totalPages, Object header) {
        super(grid, page, totalPages);
        this.header = header;
    }
}
