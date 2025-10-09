package org.apache.causeway.lab.experiments.multischema.modules.a;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
//@Table(schema="a")
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;
}
