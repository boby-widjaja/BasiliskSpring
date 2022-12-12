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
import com.basiliskSB.dto.customer.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Qualifier("customerMenu")
	@Autowired
	private CrudService service;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String company, 
			@RequestParam(defaultValue="") String contact,
			Model model){
		var pageCollection = service.getGrid(page, new CustomerFilterDTO(company, contact));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new CustomerGridDTO(row));
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(),"Customer Index", model);
		model.addAttribute("company", company);
		model.addAttribute("contact", contact);
		return "customer/customer-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			var entity = service.getUpdate(id);
			var dto = new UpsertCustomerDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Customer", model);
		} else {
			UpsertCustomerDTO dto = new UpsertCustomerDTO();
			MapperHelper.setInsertViewModel(dto, "Customer", model);
		}
		return "customer/customer-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertCustomerDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				MapperHelper.setUpdateViewModel(dto, "Customer", model);
			} else {
				MapperHelper.setInsertViewModel(dto, "Customer", model);
			}
			return "customer/customer-form";
		} else {
			service.save(dto);
			return "redirect:/customer/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		service.delete(id);
		return "redirect:/customer/index";
	}
	
}
