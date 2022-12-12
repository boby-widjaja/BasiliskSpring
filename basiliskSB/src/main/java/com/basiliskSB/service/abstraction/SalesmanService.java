package com.basiliskSB.service.abstraction;
import java.util.List;
import com.basiliskSB.dto.salesman.*;
import org.springframework.data.domain.Page;

public interface SalesmanService {
	public List<Object> getSuperiorDropdown();
	public Long dependentOrders(String employeeNumber);
	public Long dependentSubordinates(String superiorEmployeeNumber);
	public Boolean checkExistingSalesman(String employeeNumber);
	public Object getSalesmanHeader(String employeeNumber);
	public Page<Object> getRegionGridBySalesman(String employeeNumber, Integer page, String city);
	public Object assignRegion(Object dto);
	public List<Object> getRegionDropdown();
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber);
	public void detachRegionSalesman(Long regionId, String employeeNumber);
}
