package org.apache.isis.lab.experiments.multischema.modules.a;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(schema="a")
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
