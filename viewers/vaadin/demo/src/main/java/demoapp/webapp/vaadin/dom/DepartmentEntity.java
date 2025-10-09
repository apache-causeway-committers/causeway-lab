package demoapp.webapp.vaadin.dom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.springframework.lang.NonNull;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Where;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@DomainObject
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class DepartmentEntity {

    @NonNull
    // key is a reserved word in H2-SQL
    @Column(nullable = false, name = "KEY_ID")
    @PropertyLayout(hidden = Where.EVERYWHERE)
    @Id
    private String key;

    @NonNull
    @Column(nullable = false)
    @PropertyLayout(sequence = "1")
    private String name;

    // -- equal and hashcode by key
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DepartmentEntity that = (DepartmentEntity) obj;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    public String title() {
        return name;
    }

}
