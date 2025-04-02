package org.apache.causeway.lab.experiments.wktajax.home;

import java.io.ByteArrayOutputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import org.apache.wicket.model.IModel;

import org.apache.causeway.commons.internal.base._Lazy;

import lombok.SneakyThrows;

/// it seems Wicket does some magic, so it serializes this model only once per request-cylce
/// implemented with a serialization proxy to proof the fact
record PersonIModel2(_Lazy<Person> personLazy) implements IModel<Person> {

    PersonIModel2(final Person person) {
        this(_Lazy.threadSafe(()->person));
    }

    public Person person() {
        return personLazy.get();
    }

    @Override
    public Person getObject() {
        return person();
    }

    public String firstName() {
        return person().getFirstName();
    }

    public String lastName() {
        return person().getLastName();
    }

    public IModel<String> firstNameModel() {
        return this.map(Person::getFirstName);
    }

    public IModel<String> lastNameModel() {
        return this.map(Person::getLastName);
    }

    protected void testSerialization() {
        System.err.printf("PersonModel DETACH modelSizes=%d/%d, [%d]%n",
            testSerialization(firstNameModel()),
            testSerialization(lastNameModel()),
            System.identityHashCode(this));
    }

    @SneakyThrows
    int testSerialization(final Serializable model) {
        try(var bos = new ByteArrayOutputStream()) {
            var oss = new ObjectOutputStream(bos);
            oss.writeObject(model);
            bos.flush();
            return bos.toByteArray().length;
        }
    }

    @Override
    public void detach() {
        System.err.println("PersonModel DETACH");
        personLazy().clear();
    }

    // -- SERIALIZATION PROXY

    private Object writeReplace() {
        System.out.println("Serial WRITE");
        return new SerializationProxy(List.of(firstName(), lastName()));
    }

    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 1;
        private final List<String> memento;
        private SerializationProxy(final List<String> memento) {
            this.memento = memento;
        }
        private Object readResolve() {
            System.out.println("Serial READ");
            return new PersonIModel2(new Person(memento.get(0), memento.get(1)));
        }
    }

}