package com.congyuan.crm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
// Following line tells the application we are going to use JPA repository defined in this
// package, instead of auto-configured ones.
// A.K.A "com.congyuan.crm.dao"
@EnableJpaRepositories(basePackages = {
        "${spring.data.jpa.repository.packages}" })
public class DataSourceConfig {

    // Configure the main data source, use property starting with "app.datasource"
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource appDataSource() {
        return DataSourceBuilder.create().build();
    }

    // Configure the entity manager factory so that it will scan the Java entities we created
    // in: com.congyuan.crm.entity
    @Bean
    @ConfigurationProperties(prefix = "spring.data.jpa.entity")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource appDataSource) {
        return builder.dataSource(appDataSource).build();
    }

    // Configure another data source for security. Luckily spring security uses JDBC,
    // so a DataSource is all we will need.
    @Bean
    @ConfigurationProperties(prefix = "security.datasource")
    public DataSource securityDataSource() {
        return DataSourceBuilder.create().build();
    }
}