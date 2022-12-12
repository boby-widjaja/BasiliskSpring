package com.basiliskSB.service.implementation;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.service.abstraction.CategoryService;
import com.basiliskSB.service.abstraction.CrudService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.*;
import com.basiliskSB.dao.*;
import com.basiliskSB.entity.Category;

@Scope("singleton")
@Service("categoryMenu")
public class CategoryServiceImplementation implements CrudService, CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	private final int rowsInPage = 10;

	@Override
	public <T> Page<Object> getGrid(Integer page, T filter) {
		var pagination = PageRequest.of(page - 1, rowsInPage, Sort.by("id"));
		var name = MapperHelper.getStringField(filter, "name");
		var grid = categoryRepository.findAll(name, pagination);
		return grid;
	}

	@Override
	public Object getUpdate(Object id) {
		var entity = categoryRepository.findById((Long)id);
		return entity.isPresent() ? entity.get() : null;
	}

	@Override
	public Object save(Object dto) {
		var entity = new Category(
				MapperHelper.getLongField(dto, "id"),
				MapperHelper.getStringField(dto, "name"),
				MapperHelper.getStringField(dto, "description")
		);
		return categoryRepository.save(entity);
	}

	@Override
	public Boolean delete(Object id) {
		var totalDependentProducts = dependentProducts((Long)id);
		if(totalDependentProducts == 0) {
			categoryRepository.deleteById((Long)id);
			return true;
		}
		return false;
	}

	@Override
	public Long dependentProducts(Long id) {
		var totalDependentProducts = productRepository.countByCategoryId(id);
		return totalDependentProducts;
	}
	
	@Override
	public Boolean checkExistingCategoryName(Long id, String name) {
		id = (id == null) ? 0l : id;
		var totalData = categoryRepository.count(id, name);
		return (totalData > 0) ? true : false;
	}
}
