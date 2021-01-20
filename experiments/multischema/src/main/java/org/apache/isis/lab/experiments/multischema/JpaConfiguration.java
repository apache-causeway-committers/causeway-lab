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
            JpaProperties properties,
            ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(fix(dataSource), fix(properties), jtaTransactionManager);
    }

    @Override 
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() { 
        return new EclipseLinkJpaVendorAdapter(); 
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        HashMap<String, Object> jpaProps = new HashMap<>();
        jpaProps.put(PersistenceUnitProperties.WEAVING, "false");
        jpaProps.put(PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_DATABASE_SCHEMAS, "true");
        jpaProps.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
        return jpaProps;
    }
    
    @SneakyThrows
    private static DataSource fix(DataSource dataSource) {
        
        val con = dataSource.getConnection();
        
        System.err.println("catalog: " + con.getCatalog());
        System.err.println("schema: " + con.getSchema());
        
        val s = con.createStatement();
        s.execute("CREATE SCHEMA IF NOT EXISTS A");
        s.execute("CREATE SCHEMA IF NOT EXISTS B");
        con.close();
        
        //LocalContainerEntityManagerFactoryBean x;

        return dataSource;
    }
    
    private static JpaProperties fix(JpaProperties properties) {

        properties.setGenerateDdl(true);
        properties.setShowSql(true);
        
        return properties;
    }


}
