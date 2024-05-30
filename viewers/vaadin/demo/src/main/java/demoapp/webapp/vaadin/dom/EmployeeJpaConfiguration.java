package demoapp.webapp.vaadin.dom;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = { EmployeeRepository.class})
@EntityScan(basePackageClasses = { Employee.class})
public class EmployeeJpaConfiguration {
  
    @Bean
    public CommandLineRunner loadData(EmployeeRepository employeeRepository) {
        return (args) -> {
            final var bill = new Employee("Bill", "Gates", LocalDate.of(1955, 10, 28));
            bill.setDepartments(Set.of(Department.IT, Department.SALES));
            employeeRepository.save(bill);

            final var mark = new Employee("Mark", "Zuckerberg", LocalDate.of(1984, 5, 14));
            mark.setDepartments(Set.of(Department.IT, Department.MARKETING));
            employeeRepository.save(mark);

            employeeRepository.save(new Employee("Sundar", "Pichai", LocalDate.of(1972, 6, 10)));
            employeeRepository.save(new Employee("Jeff", "Bezos", LocalDate.of(1964, 1, 12)));
        };
        
    }

}
