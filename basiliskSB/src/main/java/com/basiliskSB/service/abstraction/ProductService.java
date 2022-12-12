package com.basiliskSB.service.abstraction;
import java.util.List;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.dto.utility.Dropdown;

public interface ProductService {
	public Long dependentOrderDetails(Long id);
	public List<Object> getCategoryDropdown();
	public List<Object> getSupplierDropdown();
	public String getImagePath(Long id);
}
