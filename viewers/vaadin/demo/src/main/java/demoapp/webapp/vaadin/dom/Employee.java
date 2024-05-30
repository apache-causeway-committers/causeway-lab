package demoapp.webapp.vaadin.dom;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.springframework.lang.NonNull;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor
@DomainObject
@Named(EmployeeModule.NAMESPACE + ".Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @PropertyLayout(hidden = Where.ANYWHERE)
    private Long id;

    @NonNull
    @Column(nullable = false)
    @PropertyLayout(sequence = "1")
    private String firstName;
    
    @NonNull
    @Column(nullable = false)
    @PropertyLayout(sequence = "2")
    private String lastName;

    @NonNull
    @Column(nullable = false)
    @PropertyLayout(sequence = "3")
    private LocalDate birthDate;

    @Enumerated(EnumType.ORDINAL)
    @ElementCollection(fetch = FetchType.EAGER)
    @PropertyLayout(sequence = "4")
    private Set<Department> departments = new LinkedHashSet<>();

}
