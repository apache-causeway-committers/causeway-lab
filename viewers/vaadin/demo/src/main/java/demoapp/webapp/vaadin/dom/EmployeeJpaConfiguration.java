package demoapp.webapp.vaadin.dom;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.val;

@Configuration
@EnableJpaRepositories(basePackageClasses = {EmployeeRepository.class, DepartmentEntityRepository.class})
@EntityScan(basePackageClasses = {Employee.class})
public class EmployeeJpaConfiguration {

    @Bean
    public CommandLineRunner saveDemoData(DepartmentEntityRepository departmentEntityRepository, EmployeeRepository employeeRepository) {
        return (args) -> {
            // -- departments
            departmentEntityRepository.saveAll(DepartmentEntityFixture.allValues());
            val allDeps = departmentEntityRepository.findAll();
            val it = allDeps.stream().filter(dep -> dep.getKey().equals("IT")).findFirst().get();
            val sales = allDeps.stream().filter(dep -> dep.getKey().equals("SALES")).findFirst().get();
            val marketing = allDeps.stream().filter(dep -> dep.getKey().equals("MARKETING")).findFirst().get();

            // -- tech bros
            final var bill = new Employee("Bill", "Gates", LocalDate.of(1955, 10, 28));
            bill.setDepartments(Set.of(Department.IT, Department.SALES));
            bill.addDepartmentEntity(it);
            bill.addDepartmentEntity(sales);
            employeeRepository.save(bill);

            final var mark = new Employee("Mark", "Zuckerberg", LocalDate.of(1984, 5, 14));
            mark.setDepartments(Set.of(Department.IT, Department.MARKETING));
            mark.addDepartmentEntity(it);
            mark.addDepartmentEntity(marketing);
            employeeRepository.save(mark);

            employeeRepository.save(new Employee("Sundar", "Pichai", LocalDate.of(1972, 6, 10)));
            employeeRepository.save(new Employee("Jeff", "Bezos", LocalDate.of(1964, 1, 12)));
            employeeRepository.save(new Employee("Elon", "Musk", LocalDate.of(1971, 6, 28)));
            employeeRepository.save(new Employee("Tim", "Cook", LocalDate.of(1960, 11, 1)));
            employeeRepository.save(new Employee("Satya", "Nadella", LocalDate.of(1967, 8, 19)));
            employeeRepository.save(new Employee("Larry", "Page", LocalDate.of(1973, 3, 26)));
            employeeRepository.save(new Employee("Sergey", "Brin", LocalDate.of(1973, 8, 21)));

            // -- rock stars
            employeeRepository.save(new Employee("Freddie", "Mercury", LocalDate.of(1946, 9, 5)));
            employeeRepository.save(new Employee("David", "Bowie", LocalDate.of(1947, 1, 8)));
            employeeRepository.save(new Employee("Prince", "Rogers Nelson", LocalDate.of(1958, 6, 7)));
            employeeRepository.save(new Employee("Michael", "Jackson", LocalDate.of(1958, 8, 29)));
            employeeRepository.save(new Employee("Elvis", "Presley", LocalDate.of(1935, 1, 8)));
            employeeRepository.save(new Employee("John", "Lennon", LocalDate.of(1940, 10, 9)));
            employeeRepository.save(new Employee("Paul", "McCartney", LocalDate.of(1942, 6, 18)));
            employeeRepository.save(new Employee("George", "Harrison", LocalDate.of(1943, 2, 25)));
            employeeRepository.save(new Employee("Ringo", "Starr", LocalDate.of(1940, 7, 7)));
            employeeRepository.save(new Employee("Bob", "Dylan", LocalDate.of(1941, 5, 24)));
            employeeRepository.save(new Employee("Jimi", "Hendrix", LocalDate.of(1942, 11, 27)));
            employeeRepository.save(new Employee("Eric", "Clapton", LocalDate.of(1945, 3, 30)));
            employeeRepository.save(new Employee("Jimmy", "Page", LocalDate.of(1944, 1, 9)));
            employeeRepository.save(new Employee("Robert", "Plant", LocalDate.of(1948, 8, 20)));
            employeeRepository.save(new Employee("Mick", "Jagger", LocalDate.of(1943, 7, 26)));


            // -- computer scientists
            employeeRepository.save(new Employee("Alan", "Turing", LocalDate.of(1912, 6, 23)));
            employeeRepository.save(new Employee("Grace", "Hopper", LocalDate.of(1906, 12, 9)));
            employeeRepository.save(new Employee("Ada", "Lovelace", LocalDate.of(1815, 12, 10)));
            employeeRepository.save(new Employee("John", "von Neumann", LocalDate.of(1903, 12, 28)));
            employeeRepository.save(new Employee("Donald", "Knuth", LocalDate.of(1938, 1, 10)));
            employeeRepository.save(new Employee("Dennis", "Ritchie", LocalDate.of(1941, 9, 9)));
            employeeRepository.save(new Employee("Ken", "Thompson", LocalDate.of(1943, 2, 4)));
            employeeRepository.save(new Employee("Guido", "van Rossum", LocalDate.of(1956, 1, 31)));
            employeeRepository.save(new Employee("Bjarne", "Stroustrup", LocalDate.of(1950, 12, 30)));
            employeeRepository.save(new Employee("James", "Gosling", LocalDate.of(1955, 5, 19)));
            employeeRepository.save(new Employee("Linus", "Torvalds", LocalDate.of(1969, 12, 28)));
            employeeRepository.save(new Employee("Richard", "Stallman", LocalDate.of(1953, 3, 16)));
            employeeRepository.save(new Employee("Tim", "Berners-Lee", LocalDate.of(1955, 6, 8)));
            employeeRepository.save(new Employee("Larry", "Wall", LocalDate.of(1954, 9, 27)));
            employeeRepository.save(new Employee("Anders", "Hejlsberg", LocalDate.of(1960, 12, 2)));
            employeeRepository.save(new Employee("Martin", "Odersky", LocalDate.of(1958, 9, 5)));

        };

    }

}
