package com.cts.bms.applyloan.swaggerconfig;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	
		@Bean
		public Docket swaggerConfiguration(){
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.cts.bms.applyloan"))
					.build()
					.apiInfo(apiDetails());   
			
		}
		private ApiInfo apiDetails() {
			return new ApiInfoBuilder().title("Bank Management System").description("Loan Application Service").version("1.0.0")
					.build();
	    }
	


}
