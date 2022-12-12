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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.abstraction.SalesmanService;

import java.time.LocalDate;

@Controller
@RequestMapping("/salesman")
public class SalesmanController {

	@Qualifier("salesmanMenu")
	@Autowired
	private CrudService service;

	@Autowired
	private SalesmanService salesmanService;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String employeeNumber,
			@RequestParam(defaultValue="") String name, @RequestParam(defaultValue="") String employeeLevel,
			@RequestParam(defaultValue="") String superiorName, Model model){
		var pageCollection = service.getGrid(page, new SalesmanFilterDTO(employeeNumber, name, employeeLevel, superiorName));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new SalesmanGridDTO(row));
		var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
		model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Salesman Index", model);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("name", name);
		model.addAttribute("employeeLevel", employeeLevel);
		model.addAttribute("superiorName", superiorName);
		return "salesman/salesman-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) String employeeNumber, Model model) {
		var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
		model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
		MapperHelper.setDropdownViewModel(salesmanService.getSuperiorDropdown(), "superiorDropdown", model);
		if (employeeNumber != null) {
			var entity = service.getUpdate(employeeNumber);
			var dto = new UpdateSalesmanDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Salesman", model);
		} else {
			InsertSalesmanDTO dto = new InsertSalesmanDTO();
			MapperHelper.setInsertViewModel(dto, "Salesman", model);
		}
		return "salesman/salesman-form";
	}
	
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute("salesman") InsertSalesmanDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
			model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
			MapperHelper.setDropdownViewModel(salesmanService.getSuperiorDropdown(), "superiorDropdown", model);
			MapperHelper.setInsertViewModel(dto, "Salesman", model);
			return "salesman/salesman-form";
		} else {
			service.save(dto);
			return "redirect:/salesman/index";
		}
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("salesman") UpdateSalesmanDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
			model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
			MapperHelper.setDropdownViewModel(salesmanService.getSuperiorDropdown(), "superiorDropdown", model);
			MapperHelper.setUpdateViewModel(dto, "Salesman", model);
			return "salesman/salesman-form";
		} else {
			service.save(dto);
			return "redirect:/salesman/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) String employeeNumber, Model model) {
		long dependentOrders = salesmanService.dependentOrders(employeeNumber);
		long dependentSubordinates = salesmanService.dependentSubordinates(employeeNumber);
		if(dependentOrders > 0 || dependentSubordinates > 0) {
			model.addAttribute("dependentOrders", dependentOrders);
			model.addAttribute("dependentSubordinates", dependentSubordinates);
			model.addAttribute("breadCrumbs", "Salesman Index / Fail to Delete Salesman");
			return "salesman/salesman-delete";
		}		
		service.delete(employeeNumber);
		return "redirect:/salesman/index";
	}

	private String getSalesmanFullName(Object header){
		String firstName = MapperHelper.getStringField(header, "firstName");
		String lastName = MapperHelper.getStringField(header, "lastName");
		String fullName = firstName + ((lastName != null) ? String.format(" %s", lastName) : "");
		return fullName;
	}

	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) String employeeNumber, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city, Model model) {
		var header = salesmanService.getSalesmanHeader(employeeNumber);
		var pageCollection = salesmanService.getRegionGridBySalesman(employeeNumber, page, city);
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new SalesmanDetailGridDTO(row));
		String fullName = getSalesmanFullName(header);
		String breadCrumbs = String.format("Salesman Index / Region of %s", fullName);
		MapperHelper.setDetailGridViewModel(header, grid, page, pageCollection.getTotalPages(), breadCrumbs, model);
		model.addAttribute("headerEmployeeNumber", employeeNumber);
		model.addAttribute("headerFullName", fullName);
		model.addAttribute("city", city);
		return "salesman/salesman-detail";
	}
	
	@GetMapping("/assignDetailForm")
	public String assignDetailForm(@RequestParam(required = true) String employeeNumber, Model model) {
		AssignRegionDTO dto = new AssignRegionDTO();
		dto.setSalesmanEmployeeNumber(employeeNumber);
		var header = salesmanService.getSalesmanHeader(employeeNumber);
		String fullName = getSalesmanFullName(header);
		String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", fullName);
		MapperHelper.setDropdownViewModel(salesmanService.getRegionDropdown(), "regionDropdown", model);
		model.addAttribute("dto", dto);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "salesman/salesman-detail-form";
	}
	
	@PostMapping("/assignDetail")
	public String assignDetail(@Valid @ModelAttribute("dto") AssignRegionDTO dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			var header = salesmanService.getSalesmanHeader(dto.getSalesmanEmployeeNumber());
			var fullName = getSalesmanFullName(header);
			String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", fullName);
			model.addAttribute("dto", dto);
			MapperHelper.setDropdownViewModel(salesmanService.getRegionDropdown(), "regionDropdown", model);
			model.addAttribute("breadCrumbs", breadCrumbs);
			return "salesman/salesman-detail-form";
		} else {
			salesmanService.assignRegion(dto);
			redirectAttributes.addAttribute("employeeNumber", dto.getSalesmanEmployeeNumber());
			return "redirect:/salesman/detail";
		}
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long regionId,
			@RequestParam(required = true) String employeeNumber,
			Model model, RedirectAttributes redirectAttributes) {
		salesmanService.detachRegionSalesman(regionId, employeeNumber);
		redirectAttributes.addAttribute("employeeNumber", employeeNumber);
		return "redirect:/salesman/detail";
	}
}
