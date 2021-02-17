package org.apache.isis.lab.experiments.springsecurity;

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

import lombok.val;

@Configuration 
@Import({
    
})
public class JpaConfiguration extends JpaBaseConfiguration { 

    protected JpaConfiguration(
        DataSource dataSource,
        JpaProperties jpaProperties,
        ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(dataSource, jpaProperties, jtaTransactionManager);
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


}
