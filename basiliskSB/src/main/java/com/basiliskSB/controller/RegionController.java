package com.basiliskSB.controller;
import java.util.List;
import javax.validation.Valid;

import com.basiliskSB.dto.product.UpsertProductDTO;
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
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.abstraction.RegionService;

@Controller
@RequestMapping("/region")
public class RegionController {
	@Qualifier("regionMenu")
	@Autowired
	private CrudService service;

	@Autowired
	private RegionService regionService;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city, Model model){
		var pageCollection = service.getGrid(page, new RegionFilterDTO(city));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new RegionGridDTO(row));
		model.addAttribute("city", city);
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Region Index", model);
		return "region/region-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			var entity = service.getUpdate(id);
			var dto = new UpsertRegionDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Region", model);
		} else {
			UpsertRegionDTO dto = new UpsertRegionDTO();
			MapperHelper.setInsertViewModel(dto, "Region", model);
		}
		return "region/region-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertRegionDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				MapperHelper.setUpdateViewModel(dto, "Region", model);
			} else {
				MapperHelper.setInsertViewModel(dto, "Region", model);
			}
			return "region/region-form";
		} else {
			service.save(dto);
			return "redirect:/region/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		service.delete(id);
		return "redirect:/region/index";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) Long id, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue="") String employeeNumber, @RequestParam(defaultValue="") String name,
			@RequestParam(defaultValue="") String employeeLevel, @RequestParam(defaultValue="") String superiorName,
			Model model) {
		var header = regionService.getRegionHeader(id);
		var pageCollection = regionService.getSalesmanGridByRegion(id, page, employeeNumber, name, employeeLevel, superiorName);
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new RegionDetailGridDTO(row));
		var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
		var city = MapperHelper.getStringField(header, "city");
		String breadCrumbs = String.format("Salesman of %s", city);
		MapperHelper.setDetailGridViewModel(header, grid, page, pageCollection.getTotalPages(), breadCrumbs, model);
		model.addAttribute("headerId", id);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("name", name);
		model.addAttribute("employeeLevel", employeeLevel);
		model.addAttribute("superiorName", superiorName);
		model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
		return "region/region-detail";
	}
	
	@GetMapping("/assignDetailForm")
	public String assignDetailForm(@RequestParam(required = true) Long id, Model model) {
		AssignSalesmanDTO dto = new AssignSalesmanDTO();
		var header = regionService.getRegionHeader(id);
		var city = MapperHelper.getStringField(header, "city");
		dto.setRegionId(id);
		String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", city);
		model.addAttribute("dto", dto);
		MapperHelper.setDropdownViewModel(regionService.getSalesmanDropdown(), "salesmanDropdown", model);
		model.addAttribute("breadCrumbs", breadCrumbs);
		return "region/region-detail-form";
	}
	
	@PostMapping("/assignDetail")
	public String assignDetail(@Valid @ModelAttribute("dto") AssignSalesmanDTO dto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			var header = regionService.getRegionHeader(dto.getRegionId());
			var city = MapperHelper.getStringField(header, "city");
			String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", city);
			MapperHelper.setDropdownViewModel(regionService.getSalesmanDropdown(), "salesmanDropdown", model);
			model.addAttribute("breadCrumbs", breadCrumbs);
			return "region/region-detail-form";
		} else {
			regionService.assignSalesman(dto);
			redirectAttributes.addAttribute("id", dto.getRegionId());
			return "redirect:/region/detail";
		}
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long regionId,
			@RequestParam(required = true) String employeeNumber,
			Model model, RedirectAttributes redirectAttributes) {
		regionService.detachRegionSalesman(regionId, employeeNumber);
		redirectAttributes.addAttribute("id", regionId);
		return "redirect:/region/detail";
	}
}