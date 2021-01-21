package org.apache.isis.lab.experiments.multischema.modules.b;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
//@Table(schema="b")
@NoArgsConstructor
@RequiredArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String firstName;
    
    @NonNull
    private String lastName;
}
