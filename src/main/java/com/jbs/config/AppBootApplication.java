package com.jbs.config;

/*import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@SpringBootApplication
public class AppBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppBootApplication.class, args);
    }

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/DefaultDB");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.postgresql.Driver");
                resource.setProperty("url", "jdbc:postgresql://localhost:5432/jbs");
                resource.setProperty("username", "postgres");
                resource.setProperty("password", "12345678");
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");

                context.getNamingResources().addResource(resource);
            }
        };
    }

    @Bean
    public static DataSource getDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/DefaultDB");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }
}*/

public class AppBootApplication {

}
