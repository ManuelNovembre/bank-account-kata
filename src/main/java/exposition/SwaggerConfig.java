package exposition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

	@Bean
	public Docket restAPI() {
		LOGGER.debug("initializing Swagger Docket...");
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.rest.controller"))
				.paths(PathSelectors.ant("/item")).build().apiInfo(apiInfo());
	}

	/**
	 * API Info as it appears on the swagger-ui page
	 */

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Bank account").description("manage your bank account")
				.termsOfServiceUrl("http://www.chick-fil-a.com/Legal").licenseUrl("http://www.chick-fil-a.com/Legal")
				.version("1.0").build();
	}
}
