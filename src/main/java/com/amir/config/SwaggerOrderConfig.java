package com.amir.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerOrderConfig {
	
	@Bean
	public Docket vendorDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("fxorder")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.amir.web"))
				.paths(PathSelectors.ant("/order/*"))
				.build()
				.tags(new Tag("OrderController", "Simplified FX Trading Platform controller."))
				.apiInfo(apiInfo());
		
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
	            .title("Simplified FX Trading Platform")
	            .description("Simplified FX Trading Platform for creating order and status info")
	            .license("Simplified FX Trading Platform-Apache 2.0")
	            .licenseUrl("Simplified FX Trading Platform-http://www.apache.org/licenses/LICENSE-2.0.html")
	            .termsOfServiceUrl("")
	            .version("Simplified FX Trading Platform-1.0.0")
	            .contact(new Contact("Mohd Amir", "https://github.com/amir7891", "amir87.java@gmail.com"))
	            .build();
		
	}

	

}
