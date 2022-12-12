package com.basiliskSB.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.Valid;

import com.basiliskSB.service.abstraction.CrudDetailService;
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

import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.dto.utility.Dropdown;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Qualifier("orderMenu")
	@Autowired
	private CrudService service;

	@Qualifier("orderMenu")
	@Autowired
	private CrudDetailService detailService;

	@Autowired
	private OrderService orderService;

	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String invoiceNumber, @RequestParam(required=false) Long customerId,
			@RequestParam(required=false) String employeeNumber, @RequestParam(required=false) Long deliveryId, @RequestParam(required=false) String orderDate, Model model) {
		LocalDate formattedDate = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(orderDate != null && !orderDate.equals("")) {
			formattedDate = LocalDate.parse(orderDate, formatter);
		}
		if(orderDate == null || orderDate.equals("")){
			orderDate = formatter.format(LocalDate.now());
		}
		var pageCollection = service.getGrid(page, new OrderFilterDTO(invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate));
		var grid = MapperHelper.getGridDTO(pageCollection, row -> new OrderGridDTO(row));
		MapperHelper.setGridViewModel(grid, page, pageCollection.getTotalPages(), "Order Index", model);
		MapperHelper.setDropdownViewModel(orderService.getCustomerDropdown(), "customerDropdown", model);
		MapperHelper.setDropdownViewModel(orderService.getSalesmanDropdown(), "salesmanDropdown", model);
		MapperHelper.setDropdownViewModel(orderService.getDeliveryDropdown(), "deliveryDropdown", model);
		model.addAttribute("invoiceNumber", invoiceNumber);
		model.addAttribute("customerId", customerId);
		model.addAttribute("employeeNumber", employeeNumber);
		model.addAttribute("deliveryId", deliveryId);
		model.addAttribute("orderDate", orderDate);
		return "order/order-index";
	}
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) String invoiceNumber, Model model) {
		MapperHelper.setDropdownViewModel(orderService.getCustomerDropdown(), "customerDropdown", model);
		MapperHelper.setDropdownViewModel(orderService.getSalesmanDropdown(), "salesmanDropdown", model);
		MapperHelper.setDropdownViewModel(orderService.getDeliveryDropdown(), "deliveryDropdown", model);
		if(invoiceNumber != null) {
			var entity = service.getUpdate(invoiceNumber);
			var dto = new UpdateOrderDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Order", model);
		} else {
			InsertOrderDTO dto = new InsertOrderDTO();
			MapperHelper.setInsertViewModel(dto, "Order", model);
		}
		return "order/order-form";
	}
	
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute("dto") InsertOrderDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			MapperHelper.setDropdownViewModel(orderService.getCustomerDropdown(), "customerDropdown", model);
			MapperHelper.setDropdownViewModel(orderService.getSalesmanDropdown(), "salesmanDropdown", model);
			MapperHelper.setDropdownViewModel(orderService.getDeliveryDropdown(), "deliveryDropdown", model);
			MapperHelper.setInsertViewModel(dto, "Order", model);
			return "order/order-form";
		} else {
			service.save(dto);
			return "redirect:/order/index";
		}
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("dto") UpdateOrderDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			MapperHelper.setDropdownViewModel(orderService.getCustomerDropdown(), "customerDropdown", model);
			MapperHelper.setDropdownViewModel(orderService.getSalesmanDropdown(), "salesmanDropdown", model);
			MapperHelper.setDropdownViewModel(orderService.getDeliveryDropdown(), "deliveryDropdown", model);
			MapperHelper.setUpdateViewModel(dto, "Order", model);
			return "order/order-form";
		} else {
			service.save(dto);
			return "redirect:/order/index";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) String invoiceNumber, Model model) {
		service.delete(invoiceNumber);
		return "redirect:/order/index";
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = true) String invoiceNumber, Model model) {
		var header = detailService.getHeader(invoiceNumber);
		var pageCollection = detailService.getGridDetail(invoiceNumber, page, null);
		var grid = MapperHelper.getGridDTO(pageCollection, (row) -> {
			Double unitPrice = MapperHelper.getDoubleField(row, 2);
			Integer quantity = MapperHelper.getIntegerField(row, 3);
			Double discount = MapperHelper.getDoubleField(row, 4);
			Double totalPrice = OrderService.calculateTotalPrice(unitPrice, quantity, discount);
			return new OrderDetailGridDTO(row, totalPrice);
		});
		String breadCrumbs = String.format("Order Index / Order of %s", invoiceNumber);
		MapperHelper.setDetailGridViewModel(header, grid, page, pageCollection.getTotalPages(), breadCrumbs, model);
		return "order/order-detail";
	}
	
	@GetMapping("/upsertDetailForm")
	public String upsertDetailForm(@RequestParam(required = false) Long id, @RequestParam(required = false) String invoiceNumber, Model model) {
		MapperHelper.setDropdownViewModel(orderService.getProductDropdown(), "productDropdown", model);
		if(id != null) {
			var breadCrumbs = String.format("Order of %s / Update Detail", invoiceNumber);
			var entity = detailService.getUpdateDetail(id);
			var dto = new UpsertOrderDetailDTO(entity);
			MapperHelper.setUpdateViewModel(dto, "Order", breadCrumbs, model);
		} else {
			UpsertOrderDetailDTO dto = new UpsertOrderDetailDTO();
			dto.setInvoiceNumber(invoiceNumber);
			var breadCrumbs = String.format("Order of %s / Insert Detail", invoiceNumber);
			MapperHelper.setInsertViewModel(dto, "Order", breadCrumbs, model);
		}
		return "order/order-detail-form";
	}
	
	@PostMapping("/upsertDetail")
	public String upsertDetail(@Valid @ModelAttribute("dto") UpsertOrderDetailDTO dto,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			MapperHelper.setDropdownViewModel(orderService.getProductDropdown(), "productDropdown", model);
			if(dto.getId() != null) {
				var breadCrumbs = String.format("Order of %s / Update Detail", dto.getInvoiceNumber());
				MapperHelper.setUpdateViewModel(dto, "Order", breadCrumbs, model);
			} else {
				var breadCrumbs = String.format("Order of %s / Insert Detail", dto.getInvoiceNumber());
				MapperHelper.setInsertViewModel(dto, "Order", breadCrumbs, model);
			}
			return "order/order-detail-form";
		} else {
			detailService.saveDetail(dto);
			redirectAttributes.addAttribute("invoiceNumber", dto.getInvoiceNumber());
			return "redirect:/order/detail/";
		}
	}
	
	@GetMapping("/deleteDetail")
	public String deleteDetail(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes) {
		var detail = detailService.getUpdateDetail(id);
		String invoiceNumber = MapperHelper.getStringField(detail, "invoiceNumber");
		detailService.deleteDetail(id);
		redirectAttributes.addAttribute("invoiceNumber", invoiceNumber);
		return "redirect:/order/detail/";
	}
}
