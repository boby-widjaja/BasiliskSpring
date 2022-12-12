package com.basiliskSB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MvcSecurityConfiguration{

	@Bean
    @Order(2)
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/resources/**", "/account/**").permitAll()
			.antMatchers("/delivery/**",
				"/category/upsertForm",
				"/category/delete",
				"/region/upsertForm",
				"/region/delete",
				"/region/assignDetailForm",
				"/region/deleteDetail",
				"/salesman/upsertForm",
				"/salesman/delete",
				"/salesman/assignDetailForm",
				"/salesman/deleteDetail").hasAuthority("Administrator")
			.antMatchers("/customer/**",
				"/static/resources/image/product/upsertForm",
				"/static/resources/image/product/delete").hasAnyAuthority("Administrator", "Salesman")
			.antMatchers("/order/**").hasAnyAuthority("Administrator", "Finance")
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/account/loginForm")
			.loginProcessingUrl("/authenticating")
			.and().logout()
			.and().exceptionHandling().accessDeniedPage("/account/accessDenied");
		return http.build();
	}
}
