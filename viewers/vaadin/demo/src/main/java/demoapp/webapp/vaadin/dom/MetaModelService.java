package demoapp.webapp.vaadin.dom;

import jakarta.inject.Named;

import com.vaadin.flow.component.UI;

import org.jetbrains.annotations.NotNull;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.DomainService;
import org.apache.causeway.commons.internal.reflection._ClassCache;
import org.apache.causeway.core.metamodel.specloader.SpecificationLoader;

import lombok.val;

@DomainService
@Named(MetaModelModule.NAMESPACE + ".MetaModelService")
public class MetaModelService implements BeanFactoryPostProcessor {
    @Action
    @ActionLayout(cssClassFa = "question", named = "Refresh Meta Model")
    public void refreshMetaModel() {
        _ClassCache.invalidate();
        // next lines does not work atm, bc: CausewayComponentScanInterceptorImpl is one shot only atm
        // java.lang.NullPointerException: Cannot invoke "java.util.Map.put(Object, Object)" because "this.introspectableTypes" is null
        //	at org.apache.causeway.core.config.beans.CausewayComponentScanInterceptorImpl.intercept(CausewayComponentScanInterceptorImpl.java:90) ~[causeway-core-config-3.0.0-SNAPSHOT.jar:3.0.0-SNAPSHOT]
        //	at org.apache.causeway.core.config.beans.CausewayBeanFactoryPostProcessorForSpring.postProcessBeanFactory(CausewayBeanFactoryPostProcessorForSpring.java:101) ~[causeway-core-config-3.0.0-SNAPSHOT.jar:3.0.0-SNAPSHOT]
        // >>        val causewayBeanFactoryPostProcessorForSpring = beanFactory.getBean(CausewayBeanFactoryPostProcessorForSpring.class);
        // >>       causewayBeanFactoryPostProcessorForSpring.postProcessBeanFactory(beanFactory);

        val specificationLoader = beanFactory.getBean(SpecificationLoader.class);
        specificationLoader.disposeMetaModel();
        specificationLoader.createMetaModel();
        UI.getCurrent().getPage().reload();
    }

    ConfigurableListableBeanFactory beanFactory = null;

    @Override
    public void postProcessBeanFactory(final @NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
