package org.apache.causeway.lab.experiment.wicket_spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.apache.causeway.core.config.CausewayModuleCoreConfig;

@Configuration(proxyBeanMethods = false)
@Import({
    CausewayModuleCoreConfig.class
})
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean<DelegatingFilterProxy> springSecurityFilter() {
//        var registration = new FilterRegistrationBean<DelegatingFilterProxy>();
//        registration.setFilter(new DelegatingFilterProxy("springSecurityFilterChain"));
//        registration.addInitParameter("targetFilterLifecycle", "true");
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1); // Very high priority
//        return registration;
//    }
//
//    // Wicket's filter runs AFTER Spring Security
//    @Bean
//    public FilterRegistrationBean<DelegatingFilterProxy> wicketFilterProxy(
//            final CausewaySystemEnvironment causewaySystemEnvironment,
//            final CausewayConfiguration causewayConfiguration) {
//        var registration = new FilterRegistrationBean<DelegatingFilterProxy>();
//
//        registration.setFilter(new DelegatingFilterProxy("wicketFilter"));
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10); // After security
//
//        var conf = causewayConfiguration.viewer().wicket();
//        var deploymentMode = causewaySystemEnvironment.isPrototyping()
//                ? "development"
//                : "deployment";
//        var wicketApp = conf.app();
//        var urlPattern = conf.basePath() + "*";
//
//        registration.addUrlPatterns(urlPattern);
//        registration.addInitParameter("applicationClassName", MyWebApplication.class.getName());
//        registration.addInitParameter("filterMappingUrlPattern", urlPattern);
//        registration.addInitParameter("configuration", deploymentMode);
//
//        return registration;
//    }
//
//    /**
//     * Provide the Wicket application instance to be used by the filter.
//     *
//     * If you're using @ComponentScan or Spring Boot auto-configuration, your
//     * WicketApplication class (annotated with @SpringBootApplication or similar)
//     * should already be a Spring-managed bean. This method ensures it's injected.
//     */
//    @Bean
//    public org.apache.wicket.spring.SpringWebApplicationFactory wicketAppInstance(
//            final WebApplication causewayApplication) {
//
//        // Note: Replace with your actual Wicket Application class name
//        return new org.apache.wicket.spring.SpringWebApplicationFactory();
//    }
//
//    @Bean
//    public WicketFilter wicketFilter(
//            final SpringWebApplicationFactory factory,
//            final CausewayConfiguration causewayConfiguration) {
//        var filter = new WicketFilter();
//        var conf = causewayConfiguration.viewer().wicket();
//        filter.setFilterPath(conf.basePath());
//        return filter;
//    }
//
//    public static class MyWebApplication extends WebApplication {
//        @Override
//        public Class<? extends Page> getHomePage() {
//            return BrowserInfoPage.class;
//        }
//    }

}
