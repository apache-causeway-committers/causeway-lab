package demoapp.webapp.vaadin.dom;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;

import lombok.RequiredArgsConstructor;
import lombok.val;

@DomainService
@Named(ValuesDemoModule.NAMESPACE + ".ValuesDemoService")
@RequiredArgsConstructor(onConstructor_ = { @Inject})
public class ValuesDemoService {

    private final ValuesDemoRepository valuesDemoRepository;

    @Action
    @ActionLayout(named = "Value Sample5")
    public ValuesDemoEntity valueSample() {
        val valuesDemo = new ValuesDemoEntity();
        return valuesDemoRepository.save(valuesDemo);
    }
}
