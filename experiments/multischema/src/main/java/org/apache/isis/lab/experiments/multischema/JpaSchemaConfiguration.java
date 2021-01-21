package org.apache.isis.lab.experiments.multischema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix = "isislab.jpa")
@Getter @ToString
public class JpaSchemaConfiguration {
    
    /**
     * Configuration by convention, does lookup "mapping-files" in META-INF/orm-<i>name</i>.xml
     * (equivalent to "mapping-file" entries in persistence.xml).
     */
    private final List<String> additionalSchemas = new ArrayList<>();
    
    /**
     * SQL syntax to create a DB schema.
     */
    @Setter
    private String createSchemaSqlTemplate = "CREATE SCHEMA IF NOT EXISTS %S";
    
    /**
     * Whether to automatically create additional schemas.
     */
    @Setter
    private boolean autoCreateAdditionalSchemas = true;

}
