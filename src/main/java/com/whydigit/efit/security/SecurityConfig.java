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
						"/api/usersdetails/view", "/api/master/**", "​/api​/master​/addAddress",
						"/api/master/uploadFileProofOfDelivery", "/api/master/getAllPoByPoId", "api/master/loadKitQty",
						"/api/master/getPoNoByCreateAsset", "/api/warehouse/getWarehouseLocationByOrgID",
						"/api/master/getPoByOrgId", "/api/master/createProofOfDelivery",
						"/api/warehouse/updateCreateWarehouse", "/api/warehouse/view", "/api/master/updateCreatePo",
						"/api/warehouse/getWarehouseById/{id}", "/api/partStudy/basicDetails",
						"api/warehouse/updateCreateWarehouse", "/api/partStudy/d}/searchPartStudyById",
						"/api/partStudy/packageDetail", "api/master/dmap", "/api/partStudy/packageDetail/{id}",
						"/api/partStudy/logistics", "/api/company/setup", "api/emitter/updateOutwardKitQty",
						"/api/partStudy/logistics/{id}", "/api/partStudy/**", "/api/inwardSku/**",
						"/api/master/updateCreateCnote", "/api/partStudy/stockDetail/{id}",
						"/api/emitter/createIssueRequest", "/api/emitter/*", "/api/auth/createUser",
						"/api/master/flow/getFlowByIds", "/api/master/getAllFlowName", "/api/master/updateCreateTerms",
						"/api/master/getAllTerms", "/api/master/getAllTermsById", "/api/master/updateCreatePod",
						"/api/emitter/getViewEmitterByWarehouse", "/api/emitter/getViewEmitter",
						"/api/master/getAllAssetInwardOrgId", "/api/master/getAssetByOrgId", "/api/auth/changePassword",
						"/api/auth/userByOrgId", "/api/emitter/getViewEmitterByFlow", "/api/emitter/viewEmitterInward",
						"/api/warehouse/getWarehouseByUserID", "api/master/getAssetGroupByAssetCode",
						"/api/emitter/getemitterByWarehouseId", "/api/auth/updateUser", "api/master/createAssetInward",
						"/api/emitter/emitterOutward/v1", "/api/basicMaster/city", "/api/basicMaster/country",
						"/api/master/dmap", "/api/master/stockbranchByOrgId", "/api/master/stockbranch",
						"/api/master/assettagging", "/api/master/Tagcode", "/api/basicMaster/city",
						"/api/emitter/getBinRequest", "/api/master/kitDetails", "/api/master/getAvalkitqty",
						"/api/master/updateStockBranch", "/api/basicMaster/country", "/api/basicMaster/country/{id}",
						"/api/basicMaster/city", "/api/basicMaster/city/{id}", "/api/basicMaster/state",
						"/api/basicMaster/state/{id}", "/api/master/Services", "/api/basicMaster/state/Country",
						"/api/basicMaster/city/getByStateAndCountry", "/api/emitter/proofOfDelivery","/api/emitter/getAllProofOfDelivery",
						"/api/emitter/binAllotment","/api/company/branch","/api/company/getBranchById","/api/emitter/getTaggingDetailsByRfId",
						"/api/emitter/getTaggingDetailsByTagCode","/api/master/getAvalkitqtyByBranch")
				.permitAll().antMatchers("/api/**").hasAnyRole("USER", "GUEST_USER").anyRequest().authenticated();

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
