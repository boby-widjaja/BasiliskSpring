package com.basiliskSB.service.implementation;
import java.util.*;

import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.RegionService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.RegionRepository;
import com.basiliskSB.dao.SalesmanRepository;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.entity.Region;
import com.basiliskSB.entity.Salesman;

@Scope("singleton")
@Service("regionMenu")
public class RegionServiceImplementation implements CrudService, RegionService {
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var city = MapperHelper.getStringField(filter, "city");
		var grid = regionRepository.findAll(city, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = regionRepository.findById((Long)id);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		Region entity = new Region(
			MapperHelper.getLongField(dto, "id"),
			MapperHelper.getStringField(dto, "city"),
			MapperHelper.getStringField(dto, "remark")
		);
		return regionRepository.save(entity);
	}

	@Override
	public Boolean delete(Object id) {
		try{
			regionRepository.deleteById((Long)id);
			return true;
		} catch (Exception exception){
			return false;
		}
	}

	@Override
	public Object getRegionHeader(Object id) {
		var entity = regionRepository.findById((Long)id);
		return (entity.isPresent()) ? entity.get() : null;
	}

	@Override
	public Page<Object> getSalesmanGridByRegion(Long id, Integer page, String employeeNumber, String name, String employeeLevel, String superiorName) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var grid = salesmanRepository.findAll(id, employeeNumber, name, employeeLevel, superiorName, pagination);
		return grid;
	}

	@Override
	public Object assignSalesman(Object dto) {
		var employeeNumber = MapperHelper.getStringField(dto, "salesmanEmployeeNumber");
		var regionId = MapperHelper.getLongField(dto, "regionId");
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(employeeNumber);
		Optional<Region> nullableRegion = regionRepository.findById(regionId);
		if(nullableSalesman.isPresent() && nullableRegion.isPresent()){
			Salesman salesman = nullableSalesman.get();
			Region region = nullableRegion.get();
			region.getSalesmen().add(salesman);
			return regionRepository.save(region);
		}
		return null;
	}

	@Override
	public List<Object> getSalesmanDropdown() {
		return salesmanRepository.findAllOrderByFirstName();
	}

	@Override
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber) {
		Long totalExistingRegionSalesman = regionRepository.count(employeeNumber, regionId);
		return (totalExistingRegionSalesman > 0) ? true : false;
	}

	@Override
	public Boolean detachRegionSalesman(Long regionId, String employeeNumber) {
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(employeeNumber);
		Optional<Region> nullableRegion = regionRepository.findById(regionId);
		if(nullableSalesman.isPresent() && nullableRegion.isPresent()){
			Salesman salesman = nullableSalesman.get();
			Region region = nullableRegion.get();
			region.getSalesmen().remove(salesman);
			regionRepository.save(region);
			return true;
		}
		return false;
	}
}
