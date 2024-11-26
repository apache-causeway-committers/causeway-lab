package org.apache.causeway.lab.experiments.wktajax.home;

import java.io.Serializable;

record Person(String first, String last)
implements Serializable
{
    Person(final String first, final String last) {
        this.first = first;
        this.last = last;
        System.err.printf("%s%n", "new person constructed");
    }

}