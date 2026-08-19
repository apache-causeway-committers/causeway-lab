package org.apache.causeway.lab.experiments.datajdbc;

import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.services.factory.FactoryService;

@Named("causewayLab.EmployeeMenu")
@DomainService
@DomainObjectLayout(named="Employees")
public record EmployeeMenu(
        FactoryService factoryService) {

    @Action
    @ActionLayout(cssClassFa="fa-bolt")
    public EmployeeManager employeeManager(){
        return factoryService.viewModel(EmployeeManager.class);
    }

}
