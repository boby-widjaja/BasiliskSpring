package com.basiliskSB.service.implementation;
import java.util.Optional;

import com.basiliskSB.service.abstraction.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.basiliskSB.ApplicationUserDetails;
import com.basiliskSB.MvcSecurityConfiguration;
import com.basiliskSB.dao.AccountRepository;
import com.basiliskSB.dto.account.RegisterDTO;
import com.basiliskSB.entity.Account;

@Service
public class AccountServiceImplementation implements AccountService, UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void registerAccount(RegisterDTO dto) {
		String hashPassword = passwordEncoder.encode(dto.getPassword());
		Account account = new Account(
				dto.getUsername(),
				hashPassword,
				dto.getRole());
		accountRepository.save(account);
	}

	@Override
	public String getAccountRole(String username) {
		Optional<Account> nullableEntity = accountRepository.findById(username);
		Account account = nullableEntity.get();
		return account.getRole();
	}

	@Override
	public Boolean checkExistingAccount(String username) {
		Long totalUser = accountRepository.count(username);
		return (totalUser > 0) ? true : false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> nullableEntity = accountRepository.findById(username);
		Account account = nullableEntity.get();
		return new ApplicationUserDetails(account);
	}

}
