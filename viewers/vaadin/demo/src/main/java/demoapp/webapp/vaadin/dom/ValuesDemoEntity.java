package demoapp.webapp.vaadin.dom;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Title;

import static org.apache.causeway.applib.annotation.Optionality.OPTIONAL;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DomainObject
@DomainObjectLayout(named = "Values")
@Named(ValuesDemoModule.NAMESPACE + ".ValuesDemo")
@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ValuesDemoEntity {

    @Column(nullable = true)
    @Id
    @PropertyLayout(sequence = "1")
    @Property()
    private UUID id = UUID.randomUUID();

    @Column(nullable = true)
    @PropertyLayout(sequence = "2")
    @Property(optionality = OPTIONAL)
    private String string = "Abcdefghijklmnopqrstuvwxyz";

    @Column(nullable = true)
    @PropertyLayout(sequence = "5")
    @Property(optionality = OPTIONAL)
    private Integer integer = 1234;

    @Column(nullable = true)
    @PropertyLayout(sequence = "6")
    @Property(optionality = OPTIONAL)
    private Long longValue = 1234L;

    @Column(nullable = true)
    @PropertyLayout(sequence = "7")
    @Property(optionality = OPTIONAL)
    private Short shortValue = 1234;

    @Column(nullable = true)
    @PropertyLayout(sequence = "8")
    @Property(optionality = OPTIONAL)
    private Byte byteValue = 123;

    @Column(nullable = true)
    @PropertyLayout(sequence = "9")
    @Property(optionality = OPTIONAL)
    private Character character = 'A';

    @Column(nullable = true)
    @PropertyLayout(sequence = "10")
    @Property(optionality = OPTIONAL)
    private Float floatValue = 1234.0f;

    @Column(nullable = true)
    @PropertyLayout(sequence = "11")
    @Property(optionality = OPTIONAL)
    private Double doubleValue = 1234.0;

    @Column(nullable = true)
    @PropertyLayout(sequence = "17")
    @Property(optionality = OPTIONAL)
    private BigDecimal bigDecimal = new BigDecimal("1234.999");

    @Column(nullable = true)
    @PropertyLayout(sequence = "18")
    @Property(optionality = OPTIONAL)
    private BigInteger bigInteger = new BigInteger("1234");

    @Column(nullable = true)
    @PropertyLayout(sequence = "12")
    @Property(optionality = OPTIONAL)
    private Boolean booleanValue = true;

    @Column(nullable = true)
    @PropertyLayout(sequence = "15")
    @Property(optionality = OPTIONAL)
    private URL url;

    {
        try {
            url = new URL("http://example.com");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Column(nullable = true)
    @PropertyLayout(sequence = "16")
    @Property(optionality = OPTIONAL)
    private Department department = Department.IT;

    // LocalTime, LocalDateTime

    @Column(nullable = true, name = "localtimevalue")
    @PropertyLayout(sequence = "18")
    @Property(optionality = OPTIONAL)
    private LocalTime localTime = LocalTime.of(12, 34, 56);

    @Column(nullable = true)
    @PropertyLayout(sequence = "19")
    @Property(optionality = OPTIONAL)
    private LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 23, 12, 34, 56);

    @Title
    public String title() {
        return "Values " + getId();
    }
}
