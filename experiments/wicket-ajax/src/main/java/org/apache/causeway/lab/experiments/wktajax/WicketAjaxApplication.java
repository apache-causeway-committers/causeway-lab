package org.apache.causeway.lab.experiments.wktajax;

import java.io.File;

import org.apache.causeway.commons.internal.os._OsUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WicketAjaxApplication {

    public static void main(final String[] args) throws Exception {

        _OsUtil.thereCanBeOnlyOne(new File("pid.log"));

        new SpringApplicationBuilder()
        .sources(WicketAjaxApplication.class)
        .run(args);
    }

}
