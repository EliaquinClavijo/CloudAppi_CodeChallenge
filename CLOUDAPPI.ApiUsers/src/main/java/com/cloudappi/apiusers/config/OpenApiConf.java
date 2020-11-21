package com.cloudappi.apiusers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class OpenApiConf {
	
	private final String moduleName;
	private final String moduleDescription;
	private final String apiVersion;
	
	public OpenApiConf(
	    @Value("OPENAPPI test") String moduleName,
	    @Value("Swagger for Expose endpoints") String moduleDescription,
	    @Value("v1.0") String apiVersion) {
		this.moduleName = moduleName;
	    this.apiVersion = apiVersion;
	    this.moduleDescription = moduleDescription;
	}
	
	@Bean
	public OpenAPI customOpenAPI() {
	    final String apiTitle = String.format("%s API", StringUtils.capitalize(moduleName));
		return new OpenAPI()
	        .info(new Info().title(apiTitle).version(apiVersion).description(moduleDescription));
	  }
	
}
