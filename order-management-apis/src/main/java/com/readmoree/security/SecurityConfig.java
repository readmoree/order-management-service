package com.readmoree.security;

import java.util.List;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public JwtFilter jwtFilter() {
	    return new JwtFilter();
	}
	
	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception
	{
		System.out.println("in sec filter chain");
		
		http
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.csrf(customizer -> customizer.disable())
		.authorizeHttpRequests(request -> 
        request.requestMatchers("/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html",
				"/v*/api-doc*/**","/swagger-ui/**").permitAll()
       .requestMatchers("/orders/placeOrder/**","/orders/cancel/**","/orders/return/**","/orders/exchange/**","/orders/list/**")
       .hasRole("CUSTOMER")
       .requestMatchers("/admin/**")
       .hasRole("ADMIN")        		
        .anyRequest().authenticated())  
        .sessionManagement(session 
        		-> session.sessionCreationPolicy(
        				SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtFilter(), 
				UsernamePasswordAuthenticationFilter.class);
	
    return http.build();
	}
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
		  
		 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    CorsConfiguration config = new CorsConfiguration();

		    config.setAllowedOrigins(List.of("http://localhost:3000")); // Set allowed origins
		    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		    config.setAllowCredentials(true); // Important for cookies
		    config.addExposedHeader("Authorization");

		    source.registerCorsConfiguration("/**", config);
		    
		    return source;
	    }
	

}
