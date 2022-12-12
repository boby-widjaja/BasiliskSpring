package com.basiliskSB.controller;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;

import com.basiliskSB.service.abstraction.CategoryService;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.*;
import com.basiliskSB.dto.category.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Qualifier("categoryMenu")
	@Autowired
	private CrudService service;

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, Model model){
		var pageCollection = service.getGrid(page, new CategoryFilterDTO(name));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new CategoryGridDTO(row));
		model.addAttribute("name", name);
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Category Index", model);
		return "category/category-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			var entity = service.getUpdate(id);
			var dto = new UpsertCategoryDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Category", model);
		} else {
			UpsertCategoryDTO dto = new UpsertCategoryDTO();
			MapperHelper.setInsertViewModel(dto, "Category", model);
		}
		return "category/category-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertCategoryDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				MapperHelper.setUpdateViewModel(dto, "Category", model);
			} else {
				MapperHelper.setInsertViewModel(dto, "Category", model);
			}
			return "category/category-form";
		} else {
			service.save(dto);
			return "redirect:/category/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		long dependentProducts = categoryService.dependentProducts(id);
		if(dependentProducts > 0) {
			model.addAttribute("dependencies", dependentProducts);
			model.addAttribute("breadCrumbs", "Category Index / Fail to Delete Category");
			return "category/category-delete";
		}
		service.delete(id);
		return "redirect:/category/index";
	}
}
