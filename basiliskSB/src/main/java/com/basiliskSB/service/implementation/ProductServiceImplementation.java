package com.basiliskSB.service.implementation;
import java.util.*;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.ProductService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.basiliskSB.dao.ProductRepository;
import com.basiliskSB.dao.CategoryRepository;
import com.basiliskSB.dao.OrderDetailRepository;
import com.basiliskSB.dao.SupplierRepository;
import com.basiliskSB.entity.Product;

@Scope("singleton")
@Service("productMenu")
public class ProductServiceImplementation implements CrudService, ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		String name = MapperHelper.getStringField(filter, "name");
		Long categoryId = MapperHelper.getLongField(filter, "categoryId");
		Long supplierId = MapperHelper.getLongField(filter, "supplierId");
		var grid = productRepository.findAll(name, categoryId, supplierId, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var product = productRepository.findById((Long)id);
		return product.isPresent() ? product.get() : null;
	}

	@Override
	public Object save(Object dto) {
		Product entity = new Product(
			MapperHelper.getLongField(dto, "id"),
			MapperHelper.getStringField(dto, "name"),
			MapperHelper.getLongField(dto, "supplierId"),
			MapperHelper.getLongField(dto, "categoryId"),
			MapperHelper.getStringField(dto, "description"),
			MapperHelper.getDoubleField(dto, "price"),
			MapperHelper.getIntegerField(dto, "stock"),
			MapperHelper.getIntegerField(dto, "onOrder"),
			MapperHelper.getBooleanField(dto, "discontinue"),
			MapperHelper.getStringField(dto, "imagePath")
		);
		return productRepository.save(entity);
	}

	@Override
	public Boolean delete(Object id) {
		var totalDependentOrderDetails = dependentOrderDetails((Long)id);
		if(totalDependentOrderDetails == 0) {
			productRepository.deleteById((Long)id);
			return true;
		}
		return false;
	}

	@Override
	public Long dependentOrderDetails(Long id) {
		return orderDetailRepository.countByProductId(id);
	}

	@Override
	public List<Object> getCategoryDropdown() {
		return categoryRepository.findAllOrderByName();
	}

	@Override
	public List<Object> getSupplierDropdown() {
		return supplierRepository.findAllOrderByCompanyName();
	}

	@Override
	public String getImagePath(Long id) {
		var product = productRepository.findById(id).get();
		return product.getImagePath();
	}
}
