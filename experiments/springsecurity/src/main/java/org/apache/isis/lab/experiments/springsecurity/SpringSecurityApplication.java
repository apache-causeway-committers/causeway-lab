package org.apache.isis.lab.experiments.springsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.apache.isis.lab.experiments.springsecurity.dom.Employee;
import org.apache.isis.lab.experiments.springsecurity.dom.EmployeeRepository;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(EmployeeRepository eRepo) {
	    return (args) -> {
	        eRepo.save(new Employee("Bill", "Gates"));
	        eRepo.save(new Employee("Mark", "Zuckerberg"));
	        eRepo.save(new Employee("Sundar", "Pichai"));
	        eRepo.save(new Employee("Jeff", "Bezos"));
	    };
	}
	
}
