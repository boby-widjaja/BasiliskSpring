package com.basiliskSB.controller;
import javax.validation.Valid;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.DeliveryService;
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
import com.basiliskSB.dto.delivery.*;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

	@Qualifier("deliveryMenu")
	@Autowired
	private CrudService service;

	@Autowired
	private DeliveryService deliveryService;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String company, Model model){
		var pageCollection = service.getGrid(page, new DeliveryFilterDTO(company));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new DeliveryGridDTO(row));
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Delivery Index", model);
		model.addAttribute("company", company);
		return "delivery/delivery-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		if (id != null) {
			var entity = service.getUpdate(id);
			var dto = new UpsertDeliveryDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Delivery", model);
		} else {
			UpsertDeliveryDTO dto = new UpsertDeliveryDTO();
			MapperHelper.setInsertViewModel(dto, "Delivery", model);
		}
		return "delivery/delivery-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertDeliveryDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			if(dto.getId() != null) {
				MapperHelper.setUpdateViewModel(dto, "Delivery", model);
			} else {
				MapperHelper.setInsertViewModel(dto, "Delivery", model);
			}
			return "delivery/delivery-form";
		} else {
			service.save(dto);
			return "redirect:/delivery/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) Long id, Model model) {
		long dependentOrders = deliveryService.dependentOrders(id);
		if(dependentOrders > 0) {
			model.addAttribute("dependencies", dependentOrders);
			model.addAttribute("breadCrumbs", "Delivery Index / Fail to Delete Delivery");
			return "delivery/delivery-delete";
		}
		service.delete(id);
		return "redirect:/delivery/index";
	}
}
