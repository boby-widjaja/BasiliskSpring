package com.basiliskSB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.basiliskSB.dao.AccountRepository;

@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
@SpringBootApplication
public class BasiliskSbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasiliskSbApplication.class, args);
	}

}
