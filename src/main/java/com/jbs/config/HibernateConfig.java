package com.jbs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages={"com.jbs"}, repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class)
@EnableTransactionManagement
public class HibernateConfig {

	@Value("classpath:com/jbs/sql/initial-data.sql")
	private Resource dataScript;

	@Bean
	public DataSourceInitializer executeDataSourceInitializer(final DataSource dataSource) {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(dataScript);

		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(populator);
		return initializer;
	}

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
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IllegalArgumentException, NamingException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
				new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(this.getDataSource());
		/** Uncomment this to enable Spring Boot
		 *
		 * entityManagerFactoryBean.setDataSource(AppBootApplication.getDataSource());*/

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
