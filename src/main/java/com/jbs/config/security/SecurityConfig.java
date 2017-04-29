package com.jbs.config.security;

import com.jbs.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by rizkykojek on 4/29/17.
 */
@EnableWebSecurity()
@ComponentScan("com.jbs.config")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private EmployeeRepository employeeRepository;

    protected void configure(HttpSecurity http) throws Exception {
        /** add customFilter to intercept, create manual session */
        http.addFilterBefore(new UserTokenFilter(employeeRepository), BasicAuthenticationFilter.class);

        ((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.authorizeRequests().anyRequest()).authenticated().and()).formLogin().and()).httpBasic();
    }

}
