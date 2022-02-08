package org.apache.isis.lab.experiments.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import org.apache.isis.lab.experiments.vaadin.dom.EmployeeFixture;
import org.apache.isis.lab.experiments.vaadin.viewer.LabModuleVaadinViewer;

@SpringBootApplication
@Import({
    EmployeeFixture.class,
    LabModuleVaadinViewer.class
})
public class VaadinApplication {

	public static void main(final String[] args) {

		SpringApplication.run(VaadinApplication.class, args);
	}

}
