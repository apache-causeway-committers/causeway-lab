package org.apache.isis.lab.experiments.vaadin.dom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {EmployeeRepository.class})
public class VaadinConfiguration {
  
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
