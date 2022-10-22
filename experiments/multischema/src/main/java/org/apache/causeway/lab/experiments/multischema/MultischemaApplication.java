package org.apache.causeway.lab.experiments.multischema;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.apache.causeway.lab.experiments.multischema.modules.a.Employee;
import org.apache.causeway.lab.experiments.multischema.modules.a.EmployeeRepository;
import org.apache.causeway.lab.experiments.multischema.modules.b.Customer;
import org.apache.causeway.lab.experiments.multischema.modules.b.CustomerRepository;

@SpringBootApplication
public class MultischemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultischemaApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(EmployeeRepository eRepo, CustomerRepository cRepo) {
	    return (args) -> {
	        eRepo.save(new Employee("Bill", "Gates"));
	        eRepo.save(new Employee("Mark", "Zuckerberg"));
	        eRepo.save(new Employee("Sundar", "Pichai"));
	        eRepo.save(new Employee("Jeff", "Bezos"));
	        
	        cRepo.save(new Customer("James", "Gosling"));
            cRepo.save(new Customer("Brian", "Goetz"));
	        
	    };
	    
	    
	    
	}
	
}
