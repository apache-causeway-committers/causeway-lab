package org.apache.causeway.lab.experiments.wktfcal;

import java.io.File;

import org.apache.causeway.commons.internal.os._OsUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WicketFulcalApplication {

    public static void main(final String[] args) throws Exception {

        _OsUtil.thereCanBeOnlyOne(new File("pid.log"));

        new SpringApplicationBuilder()
        .sources(WicketFulcalApplication.class)
        .run(args);
    }

}
