package com.basiliskSB.service.implementation;
import java.util.*;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.OrderRepository;
import com.basiliskSB.dao.RegionRepository;
import com.basiliskSB.dao.SalesmanRepository;
import com.basiliskSB.entity.Region;
import com.basiliskSB.entity.Salesman;

@Scope("singleton")
@Service("salesmanMenu")
public class SalesmanServiceImplementation implements CrudService, SalesmanService {

	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var employeeNumber = MapperHelper.getStringField(filter, "employeeNumber");
		var name = MapperHelper.getStringField(filter, "name");
		var employeeLevel = MapperHelper.getStringField(filter, "employeeLevel");
		var superiorName = MapperHelper.getStringField(filter,"superiorName");
		var grid = salesmanRepository.findAll(employeeNumber, name, employeeLevel, superiorName, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = salesmanRepository.findById(id.toString());
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		String superiorEmployeeNumber = MapperHelper.getStringField(dto, "superiorEmployeeNumber");
		Salesman entity = new Salesman(
			MapperHelper.getStringField(dto, "employeeNumber"),
			MapperHelper.getStringField(dto, "firstName"),
			MapperHelper.getStringField(dto, "lastName"),
			MapperHelper.getStringField(dto, "level"),
			MapperHelper.getLocalDateField(dto, "birthDate"),
			MapperHelper.getLocalDateField(dto, "hiredDate"),
			MapperHelper.getStringField(dto, "address"),
			MapperHelper.getStringField(dto, "city"),
			MapperHelper.getStringField(dto, "phone"),
			superiorEmployeeNumber
		);
		return salesmanRepository.save(entity);
	}

	@Override
	public Boolean delete(Object id) {
		try{
			salesmanRepository.deleteById(id.toString());
			return true;
		}catch (Exception exception){
			return false;
		}
	}

	@Override
	public Long dependentOrders(String employeeNumber) {
		long totalDependentOrders = orderRepository.countByEmployeeNumber(employeeNumber);
		return totalDependentOrders;
	}
	
	@Override
	public Long dependentSubordinates(String superiorEmployeeNumber) {
		long totalDependentSubordinates = salesmanRepository.countBySuperiorEmployeeNumber(superiorEmployeeNumber);
		return totalDependentSubordinates;
	}
	
	@Override
	public Object getSalesmanHeader(String employeeNumber) {
		var entity = salesmanRepository.findById(employeeNumber);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Page<Object> getRegionGridBySalesman(String employeeNumber, Integer page, String city) {
		Pageable pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		return regionRepository.findAll(employeeNumber, city, pagination);
	}

	@Override
	public Boolean checkExistingSalesman(String employeeNumber) {	
		Long totalExistingSalesman = salesmanRepository.count(employeeNumber);
		return (totalExistingSalesman > 0) ? true : false;
	}

	@Override
	public Object assignRegion(Object dto) {
		var salesmanEmployeeNumber = MapperHelper.getStringField(dto, "salesmanEmployeeNumber");
		var regionId = MapperHelper.getLongField(dto, "regionId");
		var salesman = salesmanRepository.findById(salesmanEmployeeNumber);
		var region = regionRepository.findById(regionId);
		if(salesman.isPresent() && region.isPresent()){
			salesman.get().getRegions().add(region.get());
			return salesmanRepository.save(salesman.get());
		}
		return null;
	}

	@Override
	public List<Object> getSuperiorDropdown() {
		var dropdowns = salesmanRepository.findAllOrderByFirstName();
		return dropdowns;
	}

	@Override
	public List<Object> getRegionDropdown() {
		var regionDropdown = regionRepository.findAllOrderByCity();
		return regionDropdown;
	}

	@Override
	public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber) {
		Long totalExistingRegionSalesman = salesmanRepository.count(regionId, employeeNumber);
		return (totalExistingRegionSalesman > 0) ? true : false;
	}

	@Override
	public void detachRegionSalesman(Long regionId, String employeeNumber) {
		Optional<Salesman> nullableSalesman = salesmanRepository.findById(employeeNumber);
		Salesman salesman = nullableSalesman.get();
		Optional<Region> nullableRegion = regionRepository.findById(regionId);
		Region region = nullableRegion.get();
		region.getSalesmen().remove(salesman);
		regionRepository.save(region);
	}
}
