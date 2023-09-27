package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain myAuthorization(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()).authorizeRequests(auth -> {
			auth.antMatchers("/swagger*/**", "/v*/api-docs/**", "/user/all").permitAll();
			auth.antMatchers("/product/**").permitAll();
			auth.antMatchers("/order/**").permitAll();
			
//					auth.antMatchers("/user/**").hasRole("USER");
//					auth.antMatchers("/admin").hasRole("ADMIN");
		}).httpBasic(Customizer.withDefaults()).build();
	}
	
	

//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//		return configuration.getAuthenticationManager();
//	}

}
