package org.apache.isis.lab.experiments.wicket.bootstrap;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WicketBootstrapApplication {
    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder()
        .sources(WicketBootstrapApplication.class)
        .run(args);
    }

}
