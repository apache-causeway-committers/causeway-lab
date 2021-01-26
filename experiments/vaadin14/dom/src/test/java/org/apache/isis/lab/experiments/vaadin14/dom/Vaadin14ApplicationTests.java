package org.apache.isis.lab.experiments.vaadin14.dom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {
    Vaadin14Configuration.class
})
@EnableAutoConfiguration
class Vaadin14ApplicationTests {

    @Autowired EmployeeRepository employeeRepository;
    
	@Test
	void contextLoads() {
	}
	
	@Test
	void hasEmployees() {
	    assertEquals(4, employeeRepository.findAll().size());
	}


}
