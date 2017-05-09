package com.jbs.config.security;

import com.jbs.entity.Employee;
import com.jbs.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by rizkykojek on 4/29/17.
 */
public class UserTokenFilter extends GenericFilterBean {

    private EmployeeRepository employeeRepository;

    public UserTokenFilter(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userId = httpRequest.getParameter("user");
        if (StringUtils.isNotEmpty(userId)) {
            Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
            boolean isCreateNewToken = !authentication.isPresent() ||
                    (authentication.isPresent() && !StringUtils.equals(authentication.get().getPrincipal().toString(), userId));

            if (isCreateNewToken) {
                Optional<Employee> employee = Optional.ofNullable(employeeRepository.findOne(Long.parseLong(userId)));
                if (employee.isPresent()) {
                    UserApplication userApplication = new UserApplication(httpRequest.getParameter("user"), "password", new ArrayList<>(), employee.get().getId(), employee.get().getFullName());
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(userApplication, "password", new ArrayList<>()));
                } else if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
