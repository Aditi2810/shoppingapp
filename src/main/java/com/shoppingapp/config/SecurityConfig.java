package com.shoppingapp.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.shoppingapp.security.JwtAuthenticationEntryPoint;
import com.shoppingapp.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public static final String[] PUBLIC_URLS = { "/api/v1.0/shoppingapp/register", "/api/v1.0/shoppingapp/login,"
			 };

	public static final String[] ADMIN_URLS = { "/api/v1.0/shoppingapp/product/add",
			"/api/v1.0/shoppingapp/{productName}/update/{productId}, /api/v1.0/shoppingapp/all" };

	public static final String[] PRIVATE_URLS = { "/api/v1.0/shoppingapp/all" };
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
    private JwtAuthenticationEntryPoint point;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers(ADMIN_URLS).permitAll()
				.requestMatchers(PUBLIC_URLS).permitAll()
				.requestMatchers("/api/v1.0/shoppingapp/**").permitAll())
//				.hasRole("ADMIN")
//			    .requestMatchers(PRIVATE_URLS).authenticated()
//				.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated())
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:8080"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
