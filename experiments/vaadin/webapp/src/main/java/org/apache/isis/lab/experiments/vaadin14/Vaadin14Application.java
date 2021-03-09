package org.apache.isis.lab.experiments.vaadin14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import org.apache.isis.lab.experiments.vaadin14.dom.Vaadin14Configuration;

@SpringBootApplication
@Import({
    Vaadin14Configuration.class
})
public class Vaadin14Application {

	public static void main(String[] args) {
		SpringApplication.run(Vaadin14Application.class, args);
	}
	
}
