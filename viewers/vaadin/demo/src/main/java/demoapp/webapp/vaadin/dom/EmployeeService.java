package demoapp.webapp.vaadin.dom;

import java.time.LocalDate;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;

import lombok.RequiredArgsConstructor;
import lombok.val;

@DomainService
@Named(EmployeeModule.NAMESPACE + ".EmployeeService")
@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Action
    @ActionLayout(cssClassFa="plus")
    public Employee createEmployee(String firstName, String lastName, LocalDate birthDate) {
        val employee = new Employee(firstName, lastName, birthDate);
        return employeeRepository.save(employee);
    }

    @Action
    @ActionLayout(cssClassFa="list")
    public List<Employee> listAllEmployees() {
        return employeeRepository.findAll();
    }
}
