package com.basiliskSB.service.implementation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.SupplierRepository;
import com.basiliskSB.dto.supplier.*;
import com.basiliskSB.entity.Supplier;

@Scope("singleton")
@Service("supplierMenu")
public class SupplierServiceImplementation implements CrudService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var company = MapperHelper.getStringField(filter, "company");
		var contact = MapperHelper.getStringField(filter, "contact");;
		var jobTitle = MapperHelper.getStringField(filter, "jobTitle");;
		var grid = supplierRepository.findAll(company, contact, jobTitle, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = supplierRepository.findById((Long)id);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		var entity = new Supplier(
				MapperHelper.getLongField(dto, "id"),
				MapperHelper.getStringField(dto, "companyName"),
				MapperHelper.getStringField(dto, "contactPerson"),
				MapperHelper.getStringField(dto, "jobTitle"),
				MapperHelper.getStringField(dto, "address"),
				MapperHelper.getStringField(dto, "city"),
				MapperHelper.getStringField(dto, "phone"),
				MapperHelper.getStringField(dto, "email")
		);
		return supplierRepository.save(entity);
	}

	@Override
	public Boolean delete(Object id) {
		var entity = supplierRepository.findById((Long)id);
		if(entity.isPresent()){
			entity.get().setDeleteDate(LocalDateTime.now());
			supplierRepository.save(entity.get());
			return true;
		}
		return false;
	}
}
