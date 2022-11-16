package org.apache.causeway.lab.tutorial.secman;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;

import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@Action(choicesFrom = "allEmployees")
@RequiredArgsConstructor
public class EmployeeManager_deleteEmployee {

    @Inject private EmployeeRepository employeeRepo;

    private final EmployeeManager holder;

    public EmployeeManager act(final List<Employee> employeesToRemove) {
        employeesToRemove.forEach(employeeRepo::delete);
        return holder;
    }

}
