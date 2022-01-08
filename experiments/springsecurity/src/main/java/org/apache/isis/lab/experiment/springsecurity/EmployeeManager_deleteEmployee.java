package org.apache.isis.lab.experiment.springsecurity;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotations.Action;

import lombok.RequiredArgsConstructor;

@Action(choicesFrom = "allEmployees")
@RequiredArgsConstructor
public class EmployeeManager_deleteEmployee {

    @Inject private EmployeeRepository employeeRepo;
    
    private final EmployeeManager holder;
    
    public EmployeeManager act(List<Employee> employeesToRemove) {
        employeesToRemove.forEach(employeeRepo::delete);
        return holder;
    }
    
}
