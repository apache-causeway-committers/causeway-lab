package org.apache.causeway.lab.experiments.wktajax.home;

import lombok.Data;

@Data
class Person {

    String firstName;
    String lastName;

    Person(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        System.err.printf("%s%n", "new person constructed");
    }

}