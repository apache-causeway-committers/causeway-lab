package org.apache.isis.lab.experiments.multischema;

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
})
public class JpaConfiguration extends JpaBaseConfiguration { 

    protected JpaConfiguration(
        DataSource dataSource,
        JpaProperties jpaProperties,
        ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(fix(null, dataSource), fix(jpaProperties), jtaTransactionManager);
    }

    @Override 
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() { 
        return new EclipseLinkJpaVendorAdapter(); 
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        HashMap<String, Object> jpaProps = new HashMap<>();
        jpaProps.put(PersistenceUnitProperties.WEAVING, "false");
        jpaProps.put(PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_DATABASE_SCHEMAS, true);
        
        jpaProps.put("javax.persistence.schema-generation.database.action", PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_ACTION);
        jpaProps.put("javax.persistence.schema-generation.scripts.action", PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_ACTION);
        
        jpaProps.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        return jpaProps;
        
        
        
    }
    
    @SneakyThrows
    private static DataSource fix(
            Object arg,
            DataSource dataSource
            ) {
        
        System.err.println("arg: " + arg);
        
        val con = dataSource.getConnection();
        
        System.err.println("catalog: " + con.getCatalog());
        System.err.println("schema: " + con.getSchema());
        
//        DatabaseSessionImpl session = null;
//        SchemaManager mgr = new SchemaManager(session);
//        
//        session.getPlatform().
//        
//        DatabaseObjectDefinition dod = null;
//        
//        dod.createDatabaseSchemaOnDatabase(session, null);
        
//        mgr.dropDatabaseSchemas();
        
        val s = con.createStatement();
        s.execute("CREATE SCHEMA IF NOT EXISTS A");
        s.execute("CREATE SCHEMA IF NOT EXISTS B");
        con.close();
        
        //LocalContainerEntityManagerFactoryBean x;

        return dataSource;
    }
    
    private static JpaProperties fix(JpaProperties properties) {

        System.err.println("properties: " + properties.getProperties());
        System.err.println("mapping-resources: " + properties.getMappingResources());
        
        //properties.setGenerateDdl(true);
        properties.setShowSql(true);
        
        //properties.setDatabase(Database.H2);
        properties.getMappingResources().add("META-INF/orm-a.xml");
        properties.getMappingResources().add("META-INF/orm-b.xml");
        
        return properties;
    }


}
