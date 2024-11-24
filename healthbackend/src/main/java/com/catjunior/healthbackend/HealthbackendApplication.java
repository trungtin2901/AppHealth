package com.catjunior.healthbackend;

import com.catjunior.healthbackend.security.SecurityConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@Import(SecurityConfig.class)
@ComponentScan(basePackages = {"com.catjunior.healthbackend.controllers", "com.catjunior.healthbackend.security", "com.catjunior.healthbackend.services", "com.catjunior.healthbackend.repositories", "com.catjunior.healthbackend.model", "com.catjunior.healthbackend.dto"})
public class HealthbackendApplication {

	/**
	 * Configure the ModelMapper bean.
	 *
	 * @return the ModelMapper instance
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Configure the BCryptPasswordEncoder bean.
	 *
	 * @return the BCryptPasswordEncoder instance
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * The main method to start the application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(HealthbackendApplication.class, args);
	}

}
