package com.basiliskSB.service.implementation;
import java.util.*;

import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.DeliveryService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.DeliveryRepository;
import com.basiliskSB.dao.OrderRepository;
import com.basiliskSB.dto.delivery.*;
import com.basiliskSB.entity.Delivery;

@Scope("singleton")
@Service("deliveryMenu")
public class DeliveryServiceImplementation implements CrudService, DeliveryService {

	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var company = MapperHelper.getStringField(filter, "company");
		var grid = deliveryRepository.findAll(company, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		Optional<Delivery> entity = deliveryRepository.findById((Long)id);
		return (entity.isPresent()) ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		Delivery entity = new Delivery(
			MapperHelper.getLongField(dto, "id"),
			MapperHelper.getStringField(dto, "companyName"),
			MapperHelper.getStringField(dto, "phone"),
			MapperHelper.getDoubleField(dto, "cost")
		);
		var respond = deliveryRepository.save(entity);
		return respond;
	}


	@Override
	public Boolean delete(Object id) {
		long totalDependentOrders = dependentOrders((Long)id);
		if(totalDependentOrders == 0) {
			deliveryRepository.deleteById((Long)id);
			return true;
		}
		return false;
	}

	@Override
	public Long dependentOrders(long id) {
		long totalDependentOrders = orderRepository.countByDeliveryId(id);
		return totalDependentOrders;
	}

	@Override
	public Boolean checkExistingDeliveryName(Long id, String company) {
		id = (id == null) ? 0l : id;
		Long totalExistingDelivery = deliveryRepository.count(id, company);
		return (totalExistingDelivery > 0) ? true : false;
	}
}
