package com.magic.mirror;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

public class AbstractDBSetup {

    protected DataSource initDatasource(AbstractDBConfiguration dbConfig) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbConfig.getUrl());
        dataSource.setUsername(dbConfig.getUsername());
        dataSource.setPassword(dbConfig.getPassword());
        dataSource.setDriverClassName(dbConfig.getDriverClassName());
        return dataSource;
    }

    protected void initManagerFactory(LocalContainerEntityManagerFactoryBean mfBean, AbstractDBConfiguration dbConfig) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        mfBean.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", dbConfig.getDdlAuto());
        properties.put("hibernate.dialect", dbConfig.getDialect());
        properties.put("spring.jpa.show-sql", dbConfig.getShowSql());
        mfBean.setJpaVendorAdapter(vendorAdapter);
        mfBean.setJpaPropertyMap(properties);
    }
}
