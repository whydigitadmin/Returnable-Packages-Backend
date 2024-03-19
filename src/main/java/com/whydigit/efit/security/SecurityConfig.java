                                   package com.whydigit.efit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Bean
	TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
				.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeRequests()
				.antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll()
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
						"/swagger-ui.html", "/swagger-ui/*", "/api/auth/signup", "/api/auth/login", "/api/auth/logout",
						"/api/auth/user/{userId}", "/api/auth/getRefreshToken", "/api/basicSetting/companyInfo",
						"/api/warehouse/updateWarehose", "/api/master/asset", "/api/warehouse/view/{id}",
						"/api/usersdetails/view", "/api/master/**", "​/api​/master​/addAddress","api/master/loadKitQty",
						"/api/warehouse/getWarehouseLocationByOrgID", "/api/warehouse/updateCreateWarehouse",
						"/api/warehouse/view", "/api/warehouse/getWarehouseById/{id}", "/api/partStudy/basicDetails",
						"/api/partStudy/d}/searchPartStudyById", "/api/partStudy/packageDetail",
						"/api/partStudy/packageDetail/{id}", "/api/partStudy/logistics","/api/company/setup","api/emitter/updateOutwardKitQty",
						"/api/partStudy/logistics/{id}", "/api/partStudy/**", "/api/inwardSku/**",
						"/api/partStudy/stockDetail/{id}", "/api/emitter/createIssueRequest", "/api/emitter/*",
						"/api/auth/createUser", "/api/master/flow/getFlowByIds", "/api/master/getAllFlowName",
						"/api/emitter/getViewEmitterByWarehouse", "/api/emitter/getViewEmitter","/api/auth/changePassword","/api/auth/userByOrgId",
						"/api/emitter/getViewEmitterByFlow","/api/emitter/viewEmitterInward","/api/warehouse/getWarehouseByUserID",
						"/api/emitter/getemitterByWarehouseId","/api/auth/updateUser","/api/emitter/emitterOutward/v1","/api/basicMaster/city","/api/basicMaster/country")
				.permitAll().antMatchers("/api/**").hasAnyRole("USER", "GUEST_USER").anyRequest().authenticated();

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
