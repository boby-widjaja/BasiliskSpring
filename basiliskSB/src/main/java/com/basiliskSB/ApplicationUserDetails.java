package com.basiliskSB;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.basiliskSB.entity.Account;

public class ApplicationUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	public ApplicationUserDetails(Account account) {
		this.username = account.getUsername();
		this.password = account.getPassword();
		this.authorities.add(new SimpleGrantedAuthority(account.getRole())); 
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
