package org.apache.isis.lab.experiments.vaadin14;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.isis.lab.experiments.vaadin14.dom.EmployeeRepository;

@SpringBootTest
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
