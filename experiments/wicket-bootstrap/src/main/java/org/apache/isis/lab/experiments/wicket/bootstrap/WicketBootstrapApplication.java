package org.apache.isis.lab.experiments.wicket.bootstrap;

import java.io.File;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.apache.isis.commons.internal.os._OsUtil;

@SpringBootApplication
public class WicketBootstrapApplication {

    public static void main(final String[] args) throws Exception {

        _OsUtil.thereCanBeOnlyOne(new File("pid.log"));

        new SpringApplicationBuilder()
        .sources(WicketBootstrapApplication.class)
        .run(args);
    }

}
