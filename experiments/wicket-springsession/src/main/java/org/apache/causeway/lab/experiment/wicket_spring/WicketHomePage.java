package org.apache.causeway.lab.experiment.wicket_spring;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.Assert;

@com.giffing.wicket.spring.boot.context.scan.WicketHomePage
public class WicketHomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        HttpServletRequest request = (HttpServletRequest)
                RequestCycle.get().getRequest().getContainerRequest();

        CsrfToken csrfToken =
            (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        HiddenField<String> csrfField = new HiddenField<>(
                "_csrf",
                Model.of(csrfToken != null ? csrfToken.getToken() : null));
        csrfField.setVisible(csrfToken != null);

        if(csrfToken!=null) {
            Assert.isTrue("_csrf".equals(csrfToken.getParameterName()), ()->
                "invalid template, using hardcoded parameter name '_csrf' needs update to '%s'"
                .formatted(csrfToken.getParameterName()));
        }

        add(csrfField);
    }

}
