package demoapp.webapp.vaadin.dom;

import java.util.List;

import jakarta.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.DomainService;

@DomainService
@Named(EmployeeModule.NAMESPACE + ".Employees")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Action
    List<Employee> findByLastNameStartsWithIgnoreCase(String lastName);
}
