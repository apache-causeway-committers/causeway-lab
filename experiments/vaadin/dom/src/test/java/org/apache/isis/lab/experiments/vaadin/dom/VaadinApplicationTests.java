package org.apache.isis.lab.experiments.vaadin.dom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
    VaadinConfiguration.class
})
@EnableAutoConfiguration
class VaadinApplicationTests {

    @Autowired EmployeeRepository employeeRepository;
    
	@Test
	void contextLoads() {
	}
	
	@Test
	void hasEmployees() {
	    assertEquals(4, employeeRepository.findAll().size());
	}


}
