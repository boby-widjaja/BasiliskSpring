package com.basiliskSB.service.abstraction;
import java.util.List;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;
import org.springframework.data.domain.Page;

public interface RegionService {
	public Object getRegionHeader(Object id);
	public Page<Object> getSalesmanGridByRegion(Long id, Integer page, String employeeNumber, String name, String employeeLevel, String superiorName);
	public Object assignSalesman(Object dto);
	public Boolean detachRegionSalesman(Long regionId, String employeeNumber);
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber);
	public List<Object> getSalesmanDropdown();
}
