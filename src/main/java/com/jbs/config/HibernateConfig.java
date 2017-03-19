package com.jbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages={"com.jbs"})
@EnableTransactionManagement
public class HibernateConfig {
	 
	@Bean
	public DataSource getDataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/DefaultDB");
        return dataSource;
	}
	 
	@Bean
	public JpaTransactionManager transactionManager(
			EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
 
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
				new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(this.getDataSource());
		entityManagerFactoryBean
				.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.jbs");

		entityManagerFactoryBean.setJpaProperties(this.hibProperties());

		return entityManagerFactoryBean;
	}
 
	private Properties hibProperties() {
		Properties properties = new Properties();
		/*properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.hbm2ddl.auto","create");*/
		properties.put("hibernate.dialect","org.hibernate.dialect.HANARowStoreDialect");
		properties.put("hibernate.hbm2ddl.auto","create");
		properties.put("hibernate.default_schema","KOJEK");
		return properties;
	}
}
