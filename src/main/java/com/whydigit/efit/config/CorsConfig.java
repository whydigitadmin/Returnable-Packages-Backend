package com.whydigit.efit.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

	private static final long MAX_AGE_SECONDS = 3600;

	@Value("${cors.allowed-origins}")
	private String[] allowedOrigins;

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList(allowedOrigins));
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.setMaxAge(MAX_AGE_SECONDS);
		config.addAllowedHeader("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
