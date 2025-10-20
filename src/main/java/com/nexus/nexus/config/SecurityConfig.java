package com.nexus.nexus.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private SecurityUserFilter securityUserFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors(cors -> cors.configurationSource(request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(List.of("*"));
			config.setAllowedMethods(List.of("*"));
			config.setAllowedHeaders(List.of("*"));
			return config;	
		}))
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.POST, "/user/login", "/user/registrar").permitAll()
				.requestMatchers(HttpMethod.GET, "/user/**", "/veiculos/**", 
						"/motoristas/**", "/rotas/**", "/pedido/**").authenticated()
				.requestMatchers(HttpMethod.POST, "/veiculos/", "/motoristas/", "/rotas/", "/pedido/").authenticated()
				.requestMatchers(HttpMethod.PUT, "/user/{id}", "/veiculos/{id}",
						"/motoristas/{id}", "/rotas/{id}", "/pedido/{id}").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/user/deletarUser", "/veiculos/{id}",
						"/motoristas/{id}", "/rotas/{id}", "/pedido/{id}").authenticated()
			)
			.addFilterBefore(securityUserFilter, BasicAuthenticationFilter.class);
		
		return http.build();
	}
	
}
