package demoapp.webapp.vaadin.dom;

import java.util.List;

import jakarta.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import org.apache.causeway.applib.annotation.DomainService;

@DomainService
@Named(EmployeeModule.NAMESPACE + ".Departments")
public interface DepartmentEntityRepository extends JpaRepository<DepartmentEntity, String> {
    List<DepartmentEntity> findAll();
}
