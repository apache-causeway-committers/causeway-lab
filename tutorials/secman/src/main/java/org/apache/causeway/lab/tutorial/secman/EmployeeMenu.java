package org.apache.causeway.lab.tutorial.secman;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.applib.annotation.NatureOfService;
import org.apache.causeway.applib.services.factory.FactoryService;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named("causewayLab.EmployeeMenu")
@DomainService
@DomainObjectLayout(named="Employees")
@RequiredArgsConstructor(onConstructor_ = { @Inject })
public class EmployeeMenu {

    final FactoryService factoryService;

    @Action
    @ActionLayout(cssClassFa="fa-bolt")
    public EmployeeManager employeeManager(){
        return factoryService.viewModel(EmployeeManager.class);
    }


}
