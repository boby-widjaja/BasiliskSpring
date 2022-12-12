package com.basiliskSB.service.implementation;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.CustomerRepository;
import com.basiliskSB.dto.customer.*;
import com.basiliskSB.entity.Customer;

import java.time.LocalDateTime;

@Scope("singleton")
@Service("customerMenu")
public class CustomerServiceImplementation implements CrudService {
	@Autowired
	private CustomerRepository customerRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var company = MapperHelper.getStringField(filter, "company");
		var contact = MapperHelper.getStringField(filter, "contact");
		var grid = customerRepository.findAll(company, contact, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = customerRepository.findById((Long)id);
		return (entity.isPresent()) ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		Customer entity = new Customer(
			MapperHelper.getLongField(dto, "id"),
			MapperHelper.getStringField(dto, "companyName"),
			MapperHelper.getStringField(dto, "contactPerson"),
			MapperHelper.getStringField(dto, "address"),
			MapperHelper.getStringField(dto, "city"),
			MapperHelper.getStringField(dto, "phone"),
			MapperHelper.getStringField(dto, "email")
		);
		var respond = customerRepository.save(entity);
		return respond;
	}

	@Override
	public Boolean delete(Object id) {
		try{
			var entity = customerRepository.findById((Long)id);
			if(entity.isPresent()){
				entity.get().setDeleteDate(LocalDateTime.now());
				customerRepository.save(entity.get());
				return true;
			}
			return true;
		}catch (Exception exception){
			return false;
		}
	}
}
