package org.apache.causeway.lab.experiment.wicket_spring;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.causeway.commons.internal.os._OsUtil;

@SpringBootApplication
public class WicketSpringSessionApp {

	public static void main(final String[] args) {

	    _OsUtil.thereCanBeOnlyOne(new File("pid.log"));

		SpringApplication.run(WicketSpringSessionApp.class, args);
	}

}
