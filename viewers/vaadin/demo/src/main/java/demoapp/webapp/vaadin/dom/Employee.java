package demoapp.webapp.vaadin.dom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

import lombok.val;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.springframework.lang.NonNull;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

import static org.apache.causeway.applib.annotation.SemanticsOf.IDEMPOTENT;

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
@DomainObjectLayout(describedAs = "An E. works for a corporation")
@Named(EmployeeModule.NAMESPACE + ".Employee")
public class Employee {

    public String iconName() {
        val musicians = new ArrayList<>( List.of("Lennon", "McCartney", "Harrison", "Starr", "Jagger", "Mercury", "Jackson", "Presley", "Bowie", "Dylan", "Rogers Nelson", "Hendrix", "Clapton") );
        if (musicians.contains(lastName)) return "music";
        return "briefcase";
    }

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
    @CollectionLayout(sequence = "4", hidden = Where.NOWHERE)
    @Collection(typeOf = Department.class)
    private Set<Department> departments = new LinkedHashSet<>();


    @jakarta.persistence.ManyToMany(fetch = FetchType.EAGER)
    @CollectionLayout(sequence = "4", hidden = Where.NOWHERE)
    @Collection(typeOf = DepartmentEntity.class)
    private Set<DepartmentEntity> departmentEntities = new LinkedHashSet<>();

    @Action(semantics = IDEMPOTENT)
    @ActionLayout(associateWith = "departmentEntities")
    @MemberSupport
    public void addDepartmentEntity(DepartmentEntity department) {
        departmentEntities.add(department);
    }

    @Action(semantics = IDEMPOTENT)
    @ActionLayout(associateWith = "departmentEntities")
    @MemberSupport
    public void removeDepartmentEntity(DepartmentEntity department) {
        departmentEntities.remove(department);
    }

}
