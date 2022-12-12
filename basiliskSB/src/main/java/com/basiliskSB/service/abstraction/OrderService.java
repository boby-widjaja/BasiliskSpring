package com.basiliskSB.service.abstraction;
import java.time.LocalDate;
import java.util.*;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.dto.utility.Dropdown;

public interface OrderService {
	public Boolean checkExistingOrder(String invoiceNumber);
	public List<Object> getCustomerDropdown();
	public List<Object> getSalesmanDropdown();
	public List<Object> getDeliveryDropdown();
	public List<Object> getProductDropdown();

	public static Double calculateTotalPrice(Double price, Integer quantity, Double discount) {
		Double totalPrice = ((100 - discount) / 100) * (quantity * price);
		return totalPrice;
	}
}
