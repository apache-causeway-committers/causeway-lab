package org.apache.isis.lab.experiments.wktfcal;

import java.io.File;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.apache.isis.commons.internal.os._OsUtil;

@SpringBootApplication
public class WicketFulcalApplication {

    public static void main(final String[] args) throws Exception {

        _OsUtil.thereCanBeOnlyOne(new File("pid.log"));

        new SpringApplicationBuilder()
        .sources(WicketFulcalApplication.class)
        .run(args);
    }

}
