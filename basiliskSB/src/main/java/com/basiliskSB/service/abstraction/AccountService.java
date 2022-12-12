package com.basiliskSB.service.abstraction;
import com.basiliskSB.dto.account.*;

public interface AccountService {
	public void registerAccount(RegisterDTO dto);
	public String getAccountRole(String username);
	public Boolean checkExistingAccount(String username);
}
