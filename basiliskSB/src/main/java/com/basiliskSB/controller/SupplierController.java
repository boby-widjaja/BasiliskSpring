package com.basiliskSB.controller;
import javax.validation.Valid;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.basiliskSB.dto.supplier.*;

@Qualifier("supplierMenu")
@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Qualifier("supplierMenu")
	@Autowired
	private CrudService service;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String company,
			@RequestParam(defaultValue="") String contact,
			@RequestParam(defaultValue="") String jobTitle,
			Model model){
		var pageCollection = service.getGrid(page, new SupplierFilterDTO(company, contact, jobTitle));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new SupplierGridDTO(row));
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Supplier Index", model);
		model.addAttribute("company", company);
		model.addAttribute("contact", contact);
		model.addAttribute("jobTitle", jobTitle);
		return "supplier/supplier-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			var entity = service.getUpdate(id);
			var dto = new UpsertSupplierDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Supplier", model);
		} else {
			UpsertSupplierDTO dto = new UpsertSupplierDTO();
			MapperHelper.setInsertViewModel(dto, "Supplier", model);
		}
		return "supplier/supplier-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertSupplierDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				MapperHelper.setUpdateViewModel(dto, "Supplier", model);
			} else {
				MapperHelper.setInsertViewModel(dto, "Supplier", model);
			}
			return "supplier/supplier-form";
		} else {
			service.save(dto);
			return "redirect:/supplier/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		service.delete(id);
		return "redirect:/supplier/index";
	}
}
