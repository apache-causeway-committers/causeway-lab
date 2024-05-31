package demoapp.webapp.vaadin.dom;

import java.util.UUID;

import jakarta.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;

import org.apache.causeway.applib.annotation.DomainService;

@DomainService
@Named(ValuesDemoModule.NAMESPACE + ".ValuesDemoRepository")
public interface ValuesDemoRepository extends JpaRepository<ValuesDemoEntity, UUID> {

}
