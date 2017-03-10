package com.jbs;

import com.jbs.entity.Employee;
import com.jbs.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JbsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JbsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JbsApplication.class);
	}

	@Bean
	public CommandLineRunner demo(EmployeeRepository employeeRepository){
		return (args) -> {
			// save a couple of customers
			employeeRepository.save(new Employee("Jack", "Bauer"));

			List<Employee> employees = employeeRepository.findByLastName("Bauer");
			System.out.println(employees.get(0).getFirstName() + " " + employees.get(0).getLastName());
		};
	}

}
