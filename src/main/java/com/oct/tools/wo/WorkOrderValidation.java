package com.oct.tools.wo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.oct.tools.wo.repo.CurrencyRepo;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.oct.tools.wo")
@EnableJpaRepositories(basePackageClasses = CurrencyRepo.class)
public class WorkOrderValidation {

	public static void main(String[] args) {
		SpringApplication.run(WorkOrderValidation.class, args);
	}

}