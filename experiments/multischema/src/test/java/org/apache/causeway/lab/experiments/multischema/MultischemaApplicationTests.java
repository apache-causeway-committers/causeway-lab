package org.apache.causeway.lab.experiments.multischema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.causeway.lab.experiments.multischema.modules.a.EmployeeRepository;
import org.apache.causeway.lab.experiments.multischema.modules.b.CustomerRepository;

@SpringBootTest
class MultischemaApplicationTests {

    @Autowired EmployeeRepository employeeRepository;
    @Autowired CustomerRepository customerRepository;
    
	@Test
	void contextLoads() {
	}
	
	@Test
	void hasEmployees() {
	    assertEquals(4, employeeRepository.findAll().size());
	}
	
	@Test
    void hasCustomers() {
        assertEquals(2, customerRepository.findAll().size());
    }

}
