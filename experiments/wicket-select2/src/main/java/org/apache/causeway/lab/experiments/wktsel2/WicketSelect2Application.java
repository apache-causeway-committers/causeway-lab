package org.apache.causeway.lab.experiments.wktsel2;

import java.io.File;

import org.apache.causeway.commons.internal.os._OsUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WicketSelect2Application {

    public static void main(final String[] args) throws Exception {

        _OsUtil.thereCanBeOnlyOne(new File("pid.log"));

        new SpringApplicationBuilder()
        .sources(WicketSelect2Application.class)
        .run(args);
    }

}
