package exposition.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

	@Bean
	public Docket restAPI() {
		LOGGER.debug("initializing Swagger Docket...");

		final ParameterBuilder clientIdParameterBuilder = new ParameterBuilder();
		clientIdParameterBuilder.name("clientId").modelRef(new ModelRef("string")).parameterType("header").required(true).build();
		List<Parameter> parameters = new ArrayList<>();
		parameters.add(clientIdParameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(parameters)
				.select()
				.apis(RequestHandlerSelectors.basePackage("exposition/controller"))
				.paths(PathSelectors.ant("/bank")).build().apiInfo(apiInfo());
	}

	/**
	 * API Info as it appears on the swagger-ui page
	 */

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("Bank account").description("manage your bank account")
								   .termsOfServiceUrl("").licenseUrl("")
								   .version("1.0").build();
	}
}
