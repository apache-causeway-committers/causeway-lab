package org.apache.causeway.lab.experiments.multischema;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import lombok.SneakyThrows;
import lombok.val;

@Configuration 
@Import({
    JpaSchemaConfiguration.class
})
public class JpaConfiguration extends JpaBaseConfiguration { 

    protected JpaConfiguration(
        DataSource dataSource,
        JpaSchemaConfiguration jpaSchemaConfiguration,
        JpaProperties jpaProperties,
        ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(fix(jpaSchemaConfiguration, dataSource), fix(jpaSchemaConfiguration, jpaProperties), jtaTransactionManager);
    }

    @Override 
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() { 
        return new EclipseLinkJpaVendorAdapter(); 
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        val jpaProps = new HashMap<String, Object>();
        jpaProps.put(PersistenceUnitProperties.WEAVING, "false");
        jpaProps.put(PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_DATABASE_SCHEMAS, true);
        
        jpaProps.put("javax.persistence.schema-generation.database.action", PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_ACTION);
        jpaProps.put("javax.persistence.schema-generation.scripts.action", PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_ACTION);
        
        jpaProps.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        return jpaProps;
    }
    
    @SneakyThrows
    private static DataSource fix(
            JpaSchemaConfiguration jpaSchemaConfiguration,
            DataSource dataSource) {
        
        System.err.println("jpaSchemaConfiguration: " + jpaSchemaConfiguration);
        
        if(!jpaSchemaConfiguration.getAutoCreateSchemas().isEmpty()) {
            try(val con = dataSource.getConnection()){
                
                val s = con.createStatement();
                
                for(val schema : jpaSchemaConfiguration.getAutoCreateSchemas()) {
                    s.execute(String.format(jpaSchemaConfiguration.getCreateSchemaSqlTemplate(), schema));
                }
                
            }
        }

        return dataSource;
    }
    
    private static JpaProperties fix(JpaSchemaConfiguration jpaSchemaConfiguration, JpaProperties properties) {

        System.err.println("properties: " + properties.getProperties());
        
        properties.setShowSql(true); // debug
        
        jpaSchemaConfiguration.getAdditionalOrmFiles()
        .forEach(schema->properties.getMappingResources()
                .add(String.format("META-INF/orm-%s.xml", schema)));
        
        System.err.println("mapping-resources: " + properties.getMappingResources());
        
        return properties;
    }


}
