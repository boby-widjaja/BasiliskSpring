package com.basiliskSB.controller;

import javax.validation.Valid;

import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.FileHelper;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.service.abstraction.ProductService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Qualifier("productMenu")
	@Autowired
	private CrudService service;

	@Autowired
	private ProductService productService;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, @RequestParam(required = false) Long categoryId,
						@RequestParam(required = false) Long supplierId, Model model){
		var pageCollection = service.getGrid(page, new ProductFilterDTO(name, categoryId, supplierId));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new ProductGridDTO(row));
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Product Index", model);
		model.addAttribute("name", name);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("supplierId", supplierId);
		MapperHelper.setDropdownViewModel(productService.getCategoryDropdown(), "categoryDropdown", model);
		MapperHelper.setDropdownViewModel(productService.getSupplierDropdown(), "supplierDropdown", model);
		return "product/product-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			var entity = service.getUpdate((Long)id);
			var dto = new UpsertProductDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Product", model);
		} else {
			UpsertProductDTO dto = new UpsertProductDTO();
			MapperHelper.setInsertViewModel(dto, "Product", model);
		}
		MapperHelper.setDropdownViewModel(productService.getCategoryDropdown(), "categoryDropdown", model);
		MapperHelper.setDropdownViewModel(productService.getSupplierDropdown(), "supplierDropdown", model);
		return "product/product-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertProductDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				MapperHelper.setUpdateViewModel(dto, "Product", model);
			} else {
				MapperHelper.setInsertViewModel(dto, "Product", model);
			}
			MapperHelper.setDropdownViewModel(productService.getCategoryDropdown(), "categoryDropdown", model);
			MapperHelper.setDropdownViewModel(productService.getSupplierDropdown(), "supplierDropdown", model);
			return "product/product-form";
		} else {
			MultipartFile multipartFile = dto.getImage();
			String fileName = (dto.getImagePath() == null || dto.getImagePath().equals(null)) ? null : dto.getImagePath();
			try{
				var imagePath = fileName;
				if(!multipartFile.isEmpty()){
					imagePath = FileHelper.uploadProductPhoto(fileName, multipartFile);
				}
				dto.setImagePath(imagePath);
				service.save(dto);
			} catch (Exception exception){
				return "product/product-form";
			}
			return "redirect:/product/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		long dependentOrderDetails = productService.dependentOrderDetails(id);
		if(dependentOrderDetails > 0) {
			model.addAttribute("dependencies", dependentOrderDetails);
			model.addAttribute("breadCrumbs", "Product Index / Fail to Delete Product");
			return "product/product-delete";
		}
		String imagePath = productService.getImagePath(id);
		FileHelper.deleteProductPhoto(imagePath);
		service.delete(id);
		return "redirect:/product/index";
	}
}