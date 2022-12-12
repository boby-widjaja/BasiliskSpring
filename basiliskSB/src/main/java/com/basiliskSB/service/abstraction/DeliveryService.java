package com.basiliskSB.service.abstraction;
import java.util.List;
import com.basiliskSB.dto.delivery.*;

public interface DeliveryService {
	public Long dependentOrders(long id);
	public Boolean checkExistingDeliveryName(Long id, String company);
}
