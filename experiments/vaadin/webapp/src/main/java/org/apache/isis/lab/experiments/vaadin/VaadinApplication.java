package org.apache.isis.lab.experiments.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import org.apache.isis.lab.experiments.vaadin.dom.VaadinConfiguration;

@SpringBootApplication
@Import({
    VaadinConfiguration.class
})
public class VaadinApplication {

	public static void main(String[] args) {
		SpringApplication.run(VaadinApplication.class, args);
	}
	
}
