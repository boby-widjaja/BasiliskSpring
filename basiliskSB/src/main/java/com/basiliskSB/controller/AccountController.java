package com.basiliskSB.controller;
import java.util.List;

import javax.validation.Valid;

import com.basiliskSB.dto.utility.DropdownDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.basiliskSB.dto.account.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.abstraction.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService service;
	
	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		return "account/login-form";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied(Model model) {
		return "account/access-denied";
	}
	
	@GetMapping("/registerForm")
	public String registerForm(Model model) {
		RegisterDTO dto = new RegisterDTO();
		List<DropdownDTO> roleDropdown = Dropdown.getRoleDropdown();
		model.addAttribute("roleDropdown", roleDropdown);
		model.addAttribute("account", dto);
		return "account/register-form";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("account") RegisterDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<DropdownDTO> roleDropdown = Dropdown.getRoleDropdown();
			model.addAttribute("roleDropdown", roleDropdown);
			return "account/register-form";
		}
		service.registerAccount(dto);
		return "redirect:/account/loginForm";
	}
	
}
