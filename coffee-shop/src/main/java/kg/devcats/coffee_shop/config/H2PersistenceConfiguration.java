package kg.devcats.coffee_shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:persistence-h2.properties"})
@EnableJpaRepositories(
        basePackages = "kg.devcats.coffee_shop.repository.h2",
        entityManagerFactoryRef = "h2EntityManager",
        transactionManagerRef = "h2TransactionManager"
)
public class H2PersistenceConfiguration {

    @Autowired
    private Environment env;

    @Bean("h2EntityManager")
    public LocalContainerEntityManagerFactoryBean h2EntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(h2DataSource());
        em.setPackagesToScan(
                new String[] {"kg.devcats.coffee_shop.entity.h2"
                });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("second.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("second.hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean("h2DataSource")
    public DataSource h2DataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("second.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("second.jdbc.url"));
        dataSource.setUsername(env.getProperty("second.jdbc.user"));
        dataSource.setPassword(env.getProperty("second.jdbc.pass"));

        return dataSource;
    }

    @Bean("h2PlatformTransactionManager")
    public PlatformTransactionManager h2TransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                h2EntityManager().getObject());
        return transactionManager;
    }

    @Bean("h2DataSourceInitializer")
    public DataSourceInitializer h2DataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql/data-h2.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(h2DataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}