package org.apache.causeway.lab.experiments.multischema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix = "causewaylab.jpa")
@Getter @ToString
public class JpaSchemaConfiguration {
    
    /**
     * List of additional schemas to be auto-created.
     */
    private final List<String> autoCreateSchemas = new ArrayList<>();
    
    /**
     * Does lookup additional "mapping-files" in META-INF/orm-<i>name</i>.xml
     * (equivalent to "mapping-file" entries in persistence.xml).
     */
    private final List<String> additionalOrmFiles = new ArrayList<>();
    
    /**
     * SQL syntax to create a DB schema.
     */
    @Setter
    private String createSchemaSqlTemplate = "CREATE SCHEMA IF NOT EXISTS %S";
    
}
