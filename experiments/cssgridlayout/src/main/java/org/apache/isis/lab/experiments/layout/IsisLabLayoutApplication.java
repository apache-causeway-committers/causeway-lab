package org.apache.isis.lab.experiments.layout;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "isis-lab-experiments-layout")
@PWA(
        name = "isis-lab-experiments-layout",
        shortName = "isis-lab-experiments-layout",
        offlineResources = {"images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class IsisLabLayoutApplication implements AppShellConfigurator {

    private static final long serialVersionUID = 1L;

    public static void main(final String[] args) {
        SpringApplication.run(IsisLabLayoutApplication.class, args);
    }

}
