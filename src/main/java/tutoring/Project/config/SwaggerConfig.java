package tutoring.Project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "Spring Study API";
    private static final String API_VERSION = "0.1";
    private static final String API_DESCRIPTION = "Daisy API Document";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/api/**"))
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(API_NAME)
            .version(API_VERSION)
            .description(API_DESCRIPTION)
            .build();
    }

}