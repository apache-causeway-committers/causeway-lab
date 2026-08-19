package org.apache.causeway.lab.experiments.datajdbc;

import java.util.List;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.MemberSupport;

@Action(choicesFrom = "allEmployees")
public record EmployeeManager_deleteEmployee(
        EmployeeManager mixee,
        EmployeeRepository employeeRepo) {

    @MemberSupport
    public EmployeeManager act(final List<Employee> employeesToRemove) {
        employeesToRemove.forEach(employeeRepo::delete);
        return mixee;
    }

}
